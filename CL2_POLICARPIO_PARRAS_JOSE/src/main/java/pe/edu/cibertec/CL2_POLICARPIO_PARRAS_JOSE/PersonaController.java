package pe.edu.cibertec.CL2_POLICARPIO_PARRAS_JOSE;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("personas")
public class PersonaController {
    
    PersonaRepository personaRepository; 

   public PersonaController(PersonaRepository personaRepository ){
        this.personaRepository = personaRepository; 
    }
    
    @GetMapping
    public String list(Model modelo){
        List<Persona> personas = personaRepository.findAll();
        modelo.addAttribute("listaPersonas", personas);

        return "personas/list"; 
    }

    @GetMapping("create")
    public String showCreateForm(Model model) {
        PersonaDto personaDto = new PersonaDto();
        model.addAttribute("personaForm", personaDto);
        return "personas/create";
    }

     @PostMapping
    public String create(PersonaDto personaDto) {
        Persona persona = new Persona(personaDto.getNombre(), personaDto.getApellido(),personaDto.getDni(),personaDto.getEdad());
        personaRepository.save(persona);
        
        return "redirect:/personas";
    }

    @GetMapping("{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Optional<Persona> personaOptional = personaRepository.findById(id);
        if(personaOptional.isEmpty()){
             return "404"; 
            }	

            Persona persona = personaOptional.get(); 
            model.addAttribute("persona", persona);
            return "personas/detail"; 
        }

    @GetMapping("{id}/edit")
        public String showEditForm(@PathVariable Integer id, Model model){
            Optional<Persona> personaOptional = personaRepository.findById(id);
            if(personaOptional.isEmpty()){
             return "404"; 
            }
            Persona persona = personaOptional.get(); 
            model.addAttribute("persona", persona);
               return "personas/edit"; 
        }
        
    @PostMapping("{id}")
        public String edit(@PathVariable Integer id, Persona personaDataForm){
            Optional<Persona> personaOptional = personaRepository.findById(id);
            if(personaOptional.isEmpty()){
             return "404"; 
            }

            Persona persona = personaOptional.get();
            persona.nombre = personaDataForm.getNombre();
            persona.apellido= personaDataForm.getApellido();
            persona.dni = personaDataForm.getDni();
            persona.edad = personaDataForm.getEdad();
            personaRepository.save(persona);

            return  "redirect:/personas";
        }
    @PostMapping("{id}/delete")
        public String delete(@PathVariable Integer id) {
            personaRepository.deleteById(id);
            return "redirect:/personas";
    }
}
