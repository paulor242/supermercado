import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class EmpleadoRequestDTO {
    @NotBlank
    private String cedula;

    @NotBlank
    private String nombre;

    @NotNull
    private Cargo cargo;}
