import empleados.dto.*;
import empleados.enums.Cargo;
import empleados.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService service;

    public EmpleadoController(EmpleadoService service) 
        {this.service = service;}

    @PostMapping
    public EmpleadoResponseDTO crear(@Valid @RequestBody EmpleadoRequestDTO dto) 
        {return service.crear(dto);}

    @GetMapping("/cargo")
    public List<EmpleadoResponseDTO> porCargo(@RequestParam Cargo cargo) 
        {return service.listarPorCargo(cargo);}

    @GetMapping("/fechas")
    public List<EmpleadoResponseDTO> porFechas(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fin) 
        {return service.listarPorRangoFechas(inicio, fin);}

    @GetMapping("/{id}")
    public EmpleadoResponseDTO obtener(@PathVariable Long id) 
        {return service.obtenerPorId(id);}}
