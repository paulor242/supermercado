import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {List<Empleado> findByCargo(Cargo cargo);
                                                                           List<Empleado> findByFechaIngresoBetween(LocalDate inicio, LocalDate fin);}
