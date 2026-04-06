import EmpleadoMapper;
import EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpleadoService {

    private final EmpleadoRepository repository;

    public EmpleadoService(EmpleadoRepository repository) {this.repository = repository;}

    public EmpleadoResponseDTO crear(EmpleadoRequestDTO dto) 
    {Empleado empleado = EmpleadoMapper.toEntity(dto);
     return EmpleadoMapper.toDTO(repository.save(empleado));}

    public List<EmpleadoResponseDTO> listarPorCargo(Cargo cargo) 
    {return repository.findByCargo(cargo)
     .stream()        
     .map(EmpleadoMapper::toDTO)
     .collect(Collectors.toList());}

    public List<EmpleadoResponseDTO> listarPorRangoFechas(LocalDate inicio, LocalDate fin) 
    {return repository.findByFechaIngresoBetween(inicio, fin)
     .stream()
     .map(EmpleadoMapper::toDTO)
     .collect(Collectors.toList());}

    public EmpleadoResponseDTO obtenerPorId(Long id) 
    {Empleado e = repository.findById(id)
     .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado"));
     return EmpleadoMapper.toDTO(e);}}
