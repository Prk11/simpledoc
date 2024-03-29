package ua.od.psrv.simpledoc.server;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ua.od.psrv.simpledoc.server.models.data.Categoryresolution;
import ua.od.psrv.simpledoc.server.models.data.Citizen;
import ua.od.psrv.simpledoc.server.models.data.Citizencategory;
import ua.od.psrv.simpledoc.server.models.data.Citizenstatus;
import ua.od.psrv.simpledoc.server.models.data.Delivery;
import ua.od.psrv.simpledoc.server.models.data.Docgroup;
import ua.od.psrv.simpledoc.server.models.data.DocgroupKind;
import ua.od.psrv.simpledoc.server.models.data.Organization;
import ua.od.psrv.simpledoc.server.models.data.Rubric;
import ua.od.psrv.simpledoc.server.models.data.Userentry;
import ua.od.psrv.simpledoc.server.repository.CategoryresolutionRepository;
import ua.od.psrv.simpledoc.server.repository.CitizenRepository;
import ua.od.psrv.simpledoc.server.repository.CitizencategoryRepository;
import ua.od.psrv.simpledoc.server.repository.CitizenstatusRepository;
import ua.od.psrv.simpledoc.server.repository.DeliveryRepository;
import ua.od.psrv.simpledoc.server.repository.DocgroupRepository;
import ua.od.psrv.simpledoc.server.repository.OrganizationRepository;
import ua.od.psrv.simpledoc.server.repository.RubricRepository;
import ua.od.psrv.simpledoc.server.repository.UsersRepository;

@Service
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner{
 
    @Autowired
    private UsersRepository users;

    @Autowired
    private DocgroupRepository docgroups;
    
    @Autowired
    private DeliveryRepository deliveries;
    
    @Autowired
    private CitizencategoryRepository citizencategories;
    
    @Autowired
    private CitizenstatusRepository citizenstatusies;
    
    @Autowired
    private CategoryresolutionRepository categoryresolutions;
    
    @Autowired
    private CitizenRepository citizens;
    
    @Autowired
    private OrganizationRepository organizations;

    @Autowired
    private RubricRepository rubrics;

    @Autowired
    private PasswordEncoder passwordEncoder;
    

    @Override
    public void run(String... args) {

        // User test fill
        this.users.deleteAll();
        Userentry User = new Userentry();
        
        User.setUsername("user");
        User.setFullname("Тестовый пользователь");
        User.setDepartment("Отдел тестирования");
        User.setPassword(passwordEncoder.encode("1"));
        this.users.save(User);

        // Docgroup

        this.docgroups.deleteAll();

        Docgroup pDocgroup = new Docgroup();
        pDocgroup.setName("Журнали підприємства");
        pDocgroup.setNode(false);
        pDocgroup = this.docgroups.saveAndFlush(pDocgroup);

        Docgroup p2Docgroup = new Docgroup(pDocgroup);
        p2Docgroup.setName("Журнали юридичного відділу");
        p2Docgroup = this.docgroups.saveAndFlush(p2Docgroup);

        Docgroup out2Docgroup = new Docgroup(p2Docgroup);
        out2Docgroup.setName("Журнал першої інстації");
        out2Docgroup.setIndex("02-01");
        out2Docgroup.setKind(DocgroupKind.Out);
        this.docgroups.saveAndFlush(out2Docgroup);

        Docgroup out3Docgroup = new Docgroup(p2Docgroup);
        out3Docgroup.setName("Журнал аппеляцій");
        out3Docgroup.setIndex("02-02");
        out3Docgroup.setKind(DocgroupKind.Out);
        this.docgroups.saveAndFlush(out3Docgroup);



        Docgroup inDocgroup = new Docgroup(pDocgroup);
        inDocgroup.setName("Журнал вхідних документів");
        inDocgroup.setIndex("01-01");
        inDocgroup.setShablon("{1}-{2}");
        inDocgroup.setKind(DocgroupKind.In);
        this.docgroups.saveAndFlush(inDocgroup);

        Docgroup letDocgroup = new Docgroup(pDocgroup);
        letDocgroup.setName("Журнал звернень громадян");
        letDocgroup.setNode(true);
        letDocgroup.setKind(DocgroupKind.Let);
        this.docgroups.saveAndFlush(letDocgroup);

        Docgroup outDocgroup = new Docgroup(pDocgroup);
        outDocgroup.setName("Журнал вихідних документів");
        outDocgroup.setNode(true);
        outDocgroup.setKind(DocgroupKind.Out);
        this.docgroups.saveAndFlush(outDocgroup);

        Docgroup intDocgroup = new Docgroup();
        intDocgroup.setName("Журнал внутрішніх документів");
        intDocgroup.setNode(true);
        intDocgroup.setKind(DocgroupKind.Out);
        intDocgroup.setShablon("{@}");
        intDocgroup.setDeleted(true);
        this.docgroups.saveAndFlush(intDocgroup);

        deliveries.deleteAll();

        Delivery delivery1 = new Delivery();
        delivery1.setName("Особисто");
        deliveries.save(delivery1);

        Delivery delivery2 = new Delivery();
        delivery2.setName("Поштою");
        deliveries.save(delivery2);

        Delivery delivery3 = new Delivery();
        delivery3.setName("Телеграмою");
        delivery3.setDeleted(true);
        deliveries.save(delivery3);

        // citizencategories

        citizencategories.deleteAll();

        Citizencategory citizencategory1 = new Citizencategory();
        citizencategory1.setName("Робітник");
        citizencategory1 = citizencategories.save(citizencategory1);

        Citizencategory citizencategory2 = new Citizencategory();
        citizencategory2.setName("Селянин");
        citizencategory2 = citizencategories.save(citizencategory2);

        Citizencategory citizencategory3 = new Citizencategory();
        citizencategory3.setName("Підприємец");
        citizencategory3 = citizencategories.save(citizencategory3);

        // citizenstatusies

        citizenstatusies.deleteAll();

        Citizenstatus citizenstatus1 = new Citizenstatus();
        citizenstatus1.setName("Ветеран");
        citizenstatus1 = citizenstatusies.save(citizenstatus1);

        Citizenstatus citizenstatus2 = new Citizenstatus();
        citizenstatus2.setName("Інвалід");
        citizenstatus2 = citizenstatusies.save(citizenstatus2);

        Citizenstatus citizenstatus3 = new Citizenstatus();
        citizenstatus3.setName("Інше");
        citizenstatus3 = citizenstatusies.save(citizenstatus3);

        // citizens

        citizens.deleteAll();

        Citizen citizen1 = new Citizen();
        citizen1.setFullname("Порошенко Петро Олексійович");
        citizen1.setAddress("адреса не відома, можливо у Монако)) ");
        HashSet<Citizencategory> citizen1Categories = new HashSet<>();
        citizen1Categories.add(citizencategory3);
        citizen1.setCategory(citizen1Categories);
        HashSet<Citizenstatus> citizen1Statuses = new HashSet<>();
        citizen1Statuses.add(citizenstatus2);
        citizen1Statuses.add(citizenstatus3);
        citizen1.setStatus(citizen1Statuses);
        citizen1 = citizens.save(citizen1);

        // Categoryresolution
        Categoryresolution categoryresolution1 = new Categoryresolution();
        categoryresolution1.setName("Звичайний");
        categoryresolution1 = categoryresolutions.save(categoryresolution1);
        
        Categoryresolution categoryresolution2 = new Categoryresolution();
        categoryresolution2.setName("Терміновий");
        categoryresolution2 = categoryresolutions.save(categoryresolution2);

        // Organization
         
        Organization org1 = new Organization();
        org1.setName("Рога та копита");
        org1.setCode("00000000");
        org1 = organizations.save(org1);
        
        Organization org2 = new Organization();
        org2.setName("прокат самокатів");
        org2.setCode("12345678");
        org2 = organizations.save(org2);
        
        //Rubric

        Rubric rub1f = new Rubric();
        rub1f.setNode(false);
        rub1f.setName("Перелік питань"); 
        rub1f.setCode("");
        rub1f = rubrics.save(rub1f);

        Rubric rub2f = new Rubric();
        rub2f.setNode(false);
        rub2f.setName("Вид звернень"); 
        rub2f.setCode("ВЗ");
        rub2f = rubrics.save(rub2f);

        Rubric rub3n = new Rubric();
        rub3n.setNode(true);
        rub3n.setName("Інше"); 
        rub3n.setCode("інш");
        rub3n = rubrics.save(rub3n);

        Rubric rub4f = new Rubric();
        rub4f.setNode(false);
        rub4f.setName("Пуста папка"); 
        rub4f.setCode("");
        rub4f = rubrics.save(rub4f);

        Rubric rub1f1f = new Rubric(rub1f);
        rub1f1f.setNode(false);
        rub1f1f.setName("Тестова вкладена папка питань"); 
        rub1f1f.setCode("твпп");
        rub1f1f = rubrics.save(rub1f1f);

        Rubric rub1f1f1n = new Rubric(rub1f1f);
        rub1f1f1n.setNode(true);
        rub1f1f1n.setName("Тестове питання 1"); 
        rub1f1f1n.setCode("твпп-1");
        rub1f1f1n = rubrics.save(rub1f1f1n);

        Rubric rub1f1f2n = new Rubric(rub1f1f);
        rub1f1f2n.setNode(true);
        rub1f1f2n.setName("Тестове питання 2"); 
        rub1f1f2n.setCode("твпп-2");
        rub1f1f2n = rubrics.save(rub1f1f2n);

        Rubric rub1f1n = new Rubric(rub1f);
        rub1f1n.setNode(true);
        rub1f1n.setName("ЖКГ"); 
        rub1f1n.setCode("");
        rub1f1n = rubrics.save(rub1f1n);

        Rubric rub1f2n = new Rubric(rub1f);
        rub1f2n.setNode(true);
        rub1f2n.setName("Освіта"); 
        rub1f2n.setCode("");
        rub1f2n = rubrics.save(rub1f2n);

        Rubric rub1f3n = new Rubric(rub1f);
        rub1f3n.setNode(true);
        rub1f3n.setName("Охорона здоров'я"); 
        rub1f3n.setCode("");
        rub1f3n = rubrics.save(rub1f3n);

        Rubric rub2f1n = new Rubric(rub2f);
        rub2f1n.setNode(true);
        rub2f1n.setName("Заява"); 
        rub2f1n.setCode("ВП-1");
        rub2f1n = rubrics.save(rub2f1n);

        Rubric rub2f2n = new Rubric(rub2f);
        rub2f2n.setNode(true);
        rub2f2n.setName("Скарга"); 
        rub2f2n.setCode("ВП-2");
        rub2f2n = rubrics.save(rub2f2n);


    }
}
