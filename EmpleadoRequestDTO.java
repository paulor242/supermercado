import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class EmpleadoRequestDTO {private Long id;
                                 private String cedula;
                                 private String nombre;
                                 private Cargo cargo; 
                                 private LocalDate fecha;
                                 private LocalDate fechaIngreso;
                                 private Double salario;}
