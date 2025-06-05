package org.alfred.ticketservice.dto.fare_matrix;

        import jakarta.validation.constraints.DecimalMax;
        import jakarta.validation.constraints.NotBlank;
        import jakarta.validation.constraints.NotNull;
        import jakarta.validation.constraints.PositiveOrZero;
        import jakarta.validation.constraints.Size;

        public record FareMatrixUpdateRequest(
                @NotNull(message = "Fare matrix ID is required for updates")
                Long fareMatrixId,

                @NotNull(message = "Price is required")
                @PositiveOrZero(message = "Price must be zero or positive")
                float price,

                @NotBlank(message = "Name cannot be empty")
                @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
                String name,

                boolean isActive
        ) {
        }