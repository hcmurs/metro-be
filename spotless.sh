#!/bin/bash

# Spotless Script for Metro Backend
# Usage: ./spotless.sh [command] [options]

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
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

# Function to show usage
show_usage() {
    echo "Spotless Script for Metro Backend"
    echo ""
    echo "Usage: $0 [COMMAND] [OPTIONS]"
    echo ""
    echo "Commands:"
    echo "  check                    Check code formatting (all services)"
    echo "  apply                    Apply code formatting (all services)"
    echo "  check-service <name>     Check formatting for specific service"
    echo "  apply-service <name>     Apply formatting for specific service"
    echo ""
    echo "Options:"
    echo "  -h, --help              Show this help message"
    echo "  -v, --verbose           Verbose output"
    echo ""
    echo "Available services:"
    echo "  auth, user, ticket, station, order, notification, cronjob, eureka, gateway"
    echo ""
    echo "Examples:"
    echo "  $0 check                # Check all services"
    echo "  $0 apply                # Apply formatting to all services"
    echo "  $0 check-service auth   # Check only auth service"
    echo "  $0 apply-service user   # Apply formatting to user service"
}

# Function to check if Maven is available
check_maven() {
    if ! command -v mvn &> /dev/null; then
        print_error "Maven is not installed or not in PATH"
        exit 1
    fi
}

# Function to run spotless for all services
run_spotless_all() {
    local command=$1
    local verbose=$2

    print_info "Running spotless:$command for all services..."

    if [ "$verbose" = true ]; then
        mvn spotless:$command -X
    else
        mvn spotless:$command
    fi

    if [ $? -eq 0 ]; then
        print_success "Spotless $command completed successfully for all services"
    else
        print_error "Spotless $command failed"
        exit 1
    fi
}

# Function to run spotless for a specific service
run_spotless_service() {
    local command=$1
    local service=$2
    local verbose=$3

    # Map service names to module paths
    case $service in
        auth)
            module="services/auth-service"
            ;;
        user)
            module="services/user-service"
            ;;
        ticket)
            module="services/ticket-service"
            ;;
        station)
            module="services/station-service"
            ;;
        order)
            module="services/order-service"
            ;;
        notification)
            module="services/notification-service"
            ;;
        cronjob)
            module="services/cronjob-service"
            ;;
        eureka)
            module="services/eureka-service"
            ;;
        gateway)
            module="services/gateway-service"
            ;;
        *)
            print_error "Unknown service: $service"
            echo "Available services: auth, user, ticket, station, order, notification, cronjob, eureka, gateway"
            exit 1
            ;;
    esac

    print_info "Running spotless:$command for $service service..."

    if [ ! -d "$module" ]; then
        print_error "Service directory not found: $module"
        exit 1
    fi

    cd "$module"

    if [ "$verbose" = true ]; then
        mvn spotless:$command -X
    else
        mvn spotless:$command
    fi

    local exit_code=$?
    cd - > /dev/null

    if [ $exit_code -eq 0 ]; then
        print_success "Spotless $command completed successfully for $service service"
    else
        print_error "Spotless $command failed for $service service"
        exit 1
    fi
}

# Main script logic
main() {
    local command=""
    local service=""
    local verbose=false

    # Parse arguments
    while [[ $# -gt 0 ]]; do
        case $1 in
            check)
                command="check"
                shift
                ;;
            apply)
                command="apply"
                shift
                ;;
            check-service)
                command="check-service"
                service="$2"
                shift 2
                ;;
            apply-service)
                command="apply-service"
                service="$2"
                shift 2
                ;;
            -v|--verbose)
                verbose=true
                shift
                ;;
            -h|--help)
                show_usage
                exit 0
                ;;
            *)
                print_error "Unknown option: $1"
                show_usage
                exit 1
                ;;
        esac
    done

    # Check if command is provided
    if [ -z "$command" ]; then
        print_error "No command provided"
        show_usage
        exit 1
    fi

    # Check if service is provided for service-specific commands
    if [[ "$command" == *"-service" ]] && [ -z "$service" ]; then
        print_error "Service name is required for $command command"
        show_usage
        exit 1
    fi

    # Check Maven availability
    check_maven

    # Execute the appropriate command
    case $command in
        check)
            run_spotless_all "check" $verbose
            ;;
        apply)
            run_spotless_all "apply" $verbose
            ;;
        check-service)
            run_spotless_service "check" "$service" $verbose
            ;;
        apply-service)
            run_spotless_service "apply" "$service" $verbose
            ;;
        *)
            print_error "Unknown command: $command"
            show_usage
            exit 1
            ;;
    esac
}

# Run the main function with all arguments
main "$@"
