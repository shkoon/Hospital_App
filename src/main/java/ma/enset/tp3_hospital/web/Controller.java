package ma.enset.tp3_hospital.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.enset.tp3_hospital.entities.Patient;
import ma.enset.tp3_hospital.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@org.springframework.stereotype.Controller
@AllArgsConstructor
public class Controller {

    private PatientRepository patientRepository;

    @GetMapping("/user/index")
    public String index(Model model, @RequestParam(name = "page",defaultValue = "0") int page,
                        @RequestParam(name = "size",defaultValue = "4") int size,
                        @RequestParam(name = "keyword",defaultValue = "") String keyword){
        Page<Patient> patientPage=patientRepository.findByNomContains(keyword,PageRequest.of(page,size));
        model.addAttribute("listePatients",patientPage.getContent());
        model.addAttribute("pages",new int [patientPage.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "patients";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/delete")
    public String delete(Long id,String keyword,int page){
        patientRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/formPatients")
    public String formPatients(Model model){
        model.addAttribute("patient",new Patient());
        return "formPatients";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/editPatients")
    public String editPatients(Model model,Long id,String keyword,int page){
        Patient patient=patientRepository.findById(id).orElse(null);
        if( patient==null) throw  new RuntimeException("Patient Introuvable");
        model.addAttribute("patient",patient);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);
        return  "editPatients";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/save")
    public String save(Model model, @Valid Patient patient, BindingResult bindingResult,String keyword,int page){
        if(bindingResult.hasErrors()){
            return "formPatients";
        }
        patientRepository.save(patient);

        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

}
