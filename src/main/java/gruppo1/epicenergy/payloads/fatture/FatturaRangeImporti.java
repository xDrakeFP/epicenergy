package gruppo1.epicenergy.payloads.fatture;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record FatturaRangeImporti(@NotNull @DecimalMin(value = "0.01") double min, @NotNull @DecimalMin(value = "0.01") double max) {
}
