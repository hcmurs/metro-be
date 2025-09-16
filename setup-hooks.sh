#!/bin/bash

# Git Hooks Setup Script for Metro Backend
# This script sets up git hooks for automatic code formatting

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

print_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Get the repository root
REPO_ROOT=$(git rev-parse --show-toplevel)
HOOKS_DIR="$REPO_ROOT/.git/hooks"

print_info "Setting up Git hooks for Metro Backend..."
print_info "Repository root: $REPO_ROOT"

# Create pre-commit hook
PRE_COMMIT_HOOK="$HOOKS_DIR/pre-commit"

cat > "$PRE_COMMIT_HOOK" << 'EOF'
#!/bin/bash
# Pre-commit hook for running Spotless formatting

# Navigate to the repository root
cd "$(git rev-parse --show-toplevel)"

echo "🔧 Running Spotless formatting..."

# Check if any Java files are staged
if git diff --cached --name-only | grep -q '\.java$'; then
    # Run spotless apply to format code before commit
    if ./spotless.sh apply -q; then
        echo "✅ Spotless formatting completed successfully!"
        
        # Add the formatted files back to the commit
        git add -A
        
        echo "📝 Formatted files added to commit"
    else
        echo "❌ Spotless formatting failed!"
        echo "Please fix the formatting issues and try again."
        exit 1
    fi
else
    echo "ℹ️  No Java files to format"
fi

echo "✨ Pre-commit checks passed!"
EOF

# Make the hook executable
chmod +x "$PRE_COMMIT_HOOK"

print_success "Pre-commit hook installed at: $PRE_COMMIT_HOOK"

# Create commit-msg hook for conventional commits (optional)
COMMIT_MSG_HOOK="$HOOKS_DIR/commit-msg"

cat > "$COMMIT_MSG_HOOK" << 'EOF'
#!/bin/bash
# Commit message hook for conventional commits validation

commit_regex='^(feat|fix|docs|style|refactor|perf|test|chore|build|ci)(\(.+\))?: .{1,50}'

if ! grep -qE "$commit_regex" "$1"; then
    echo "❌ Invalid commit message format!"
    echo ""
    echo "Commit message should follow conventional commits format:"
    echo "  <type>[optional scope]: <description>"
    echo ""
    echo "Examples:"
    echo "  feat: add user authentication"
    echo "  fix(auth): resolve login issue"
    echo "  docs: update README"
    echo "  refactor(service): improve code structure"
    echo ""
    echo "Allowed types: feat, fix, docs, style, refactor, perf, test, chore, build, ci"
    exit 1
fi
EOF

chmod +x "$COMMIT_MSG_HOOK"

print_success "Commit message hook installed at: $COMMIT_MSG_HOOK"

# Test the hooks
print_info "Testing hook setup..."

if [ -x "$PRE_COMMIT_HOOK" ] && [ -x "$COMMIT_MSG_HOOK" ]; then
    print_success "All hooks are installed and executable!"
else
    print_error "Some hooks are not properly installed"
    exit 1
fi

print_info "🎉 Git hooks setup completed!"
echo ""
echo "Available hooks:"
echo "  ✅ pre-commit: Runs Spotless formatting before each commit"
echo "  ✅ commit-msg: Validates commit message format (conventional commits)"
echo ""
echo "To disable hooks temporarily, use: git commit --no-verify"
