package com.ProjectHub.service;

import com.ProjectHub.model.Semester;
import com.ProjectHub.repository.SemesterRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.UUID;

@Service
public class SemesterService {

    @Autowired
    private SemesterRepository semesterRepository;

    @PostConstruct
    public void popularSemestresDefault() {
        if (semesterRepository.count() == 0) {
            int currentYear = Year.now().getValue();

            for (short i = 1; i <= 10; i++) {
                short semestreAtual = (short) ((i - 1) % 2 + 1); // alterna entre 1 e 2
                int anoSemestre = currentYear - ((i-1)/2); // Ex: para i=1/2 ano atual, i=3/4 ano passado...

                LocalDate start, end;
                if (semestreAtual == 1) {
                    start = LocalDate.of(anoSemestre, Month.FEBRUARY, 1);   // Fevereiro a Junho
                    end   = LocalDate.of(anoSemestre, Month.JUNE, 30);
                } else {
                    start = LocalDate.of(anoSemestre, Month.AUGUST, 1);    // Agosto a Dezembro
                    end   = LocalDate.of(anoSemestre, Month.DECEMBER, 20);
                }

                semesterRepository.save(Semester.builder()
                        .number(semestreAtual)
                        .year((short) anoSemestre)
                        .startDate(Timestamp.valueOf(start.atStartOfDay()))
                        .endDate(Timestamp.valueOf(end.atStartOfDay()))
                        .build()
                );
            }
        }
    }

    public List<Semester> listarTodos() {
        return semesterRepository.findAll();
    }

    public Semester buscarPorId(UUID id) {
        return semesterRepository.findById(id).orElse(null);
    }

    public Semester salvar(Semester semester) {
        return semesterRepository.save(semester);
    }

    public void deletar(UUID id) {
        semesterRepository.deleteById(id);
    }
}