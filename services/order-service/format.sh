#!/bin/bash

# Spotless helper script for code formatting and linting

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Help function
show_help() {
    echo "Spotless Code Formatter and Linter"
    echo ""
    echo "Usage: $0 [COMMAND]"
    echo ""
    echo "Commands:"
    echo "  check     - Check if code is formatted correctly (default)"
    echo "  apply     - Apply formatting fixes to all files"
    echo "  kotlin    - Check only Kotlin files"
    echo "  java      - Check only Java files"
    echo "  help      - Show this help message"
    echo ""
    echo "Examples:"
    echo "  $0 check          # Check all files"
    echo "  $0 apply          # Format all files"
    echo "  $0 kotlin         # Check only Kotlin files"
}

# Default command
COMMAND=${1:-check}

case $COMMAND in
    "check")
        print_status "Checking code formatting with Spotless..."
        ./mvnw spotless:check
        print_status "✅ Code formatting check completed!"
        ;;
    "apply")
        print_status "Applying Spotless formatting to all files..."
        ./mvnw spotless:apply
        print_status "✅ Code formatting applied successfully!"
        ;;
    "kotlin")
        print_status "Checking Kotlin files only..."
        ./mvnw spotless:check -Dspotless.check.skip=false
        print_status "✅ Kotlin formatting check completed!"
        ;;
    "java")
        print_status "Checking Java files only..."
        ./mvnw spotless:check -Dspotless.check.skip=false
        print_status "✅ Java formatting check completed!"
        ;;
    "help"|"-h"|"--help")
        show_help
        ;;
    *)
        print_error "Unknown command: $COMMAND"
        echo ""
        show_help
        exit 1
        ;;
esac
