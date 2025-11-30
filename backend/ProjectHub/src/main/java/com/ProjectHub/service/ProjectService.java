package com.ProjectHub.service;

import com.ProjectHub.dto.ProjectBasicUpdateDTO;
import com.ProjectHub.model.Project;
import com.ProjectHub.model.ProjectMember;
import com.ProjectHub.model.User;
import com.ProjectHub.repository.UserRepository;
import com.ProjectHub.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Project> listarTodos() {
        return projectRepository.findAll();
    }

    public Project buscarPorId(UUID id) {
        return projectRepository.findByIdFetchMembers(id).orElse(null);
    }


    public Project salvar(Project project, User creator) {
        if (project.getCreatedAt() == null) {
            project.setCreatedAt(Timestamp.from(Instant.now()));
        }
        if (project.getProjectId() == null && creator != null) {
            // Adiciona o criador à lista de membros se o projeto está sendo criado
            ProjectMember pm = new ProjectMember(null, project, creator, "member", Timestamp.from(Instant.now()));
            if (project.getMembers() == null) {
                project.setMembers(new ArrayList<>());
            }
            project.getMembers().add(pm);
            project.setCreatedBy(creator);
        }
        return projectRepository.save(project);
    }


    public void atualizarBasicoEIntegrantes(Project projeto, ProjectBasicUpdateDTO req) {
        projeto.setImageUrl(req.getImageUrl());
        projeto.setGroupName(req.getGroupName());

        if (req.getMemberIds() != null) {
            List<User> novosMembros = userRepository.findAllById(req.getMemberIds());
            // supondo que Project tem List<ProjectMember> members
            List<ProjectMember> lista = novosMembros.stream()
                    .map(u -> new ProjectMember(null, projeto, u, "member", Timestamp.from(Instant.now())))
                    .collect(Collectors.toList());
            projeto.setMembers(lista);
        }

        projectRepository.save(projeto);
    }

    public void salvarBanner(Project projeto, MultipartFile file) {
        try {
            String originalName = Optional.ofNullable(file.getOriginalFilename()).orElse("banner.png");
            String ext = originalName.contains(".")
                    ? originalName.substring(originalName.lastIndexOf("."))
                    : ".png";

            String fileName = projeto.getProjectId() + "-banner" + ext;
            Path dir = Paths.get("uploads/banners").toAbsolutePath().normalize();
            Files.createDirectories(dir);
            Path target = dir.resolve(fileName);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            // URL pública (pode ser relativa)
            String publicUrl = "http://localhost:8080/api/files/banners/" + fileName;
            projeto.setImageUrl(publicUrl);
            projectRepository.save(projeto);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar banner", e);
        }
    }



    public boolean podeEditarProjeto(Project projeto, User requester) {
        if (requester == null) return false;

        boolean isAdmin = "admin".equalsIgnoreCase(requester.getRole());

        boolean isCreator = projeto.getCreatedBy() != null &&
                projeto.getCreatedBy().getUserId().equals(requester.getUserId());

        boolean isMember = projeto.getMembers() != null &&
                projeto.getMembers().stream()
                        .anyMatch(pm -> pm.getUser().getUserId().equals(requester.getUserId()));

        return isAdmin || isCreator || isMember;
    }




    public void gerarQrCode(Project projeto, String frontBaseUrl) {
        // URL pública da página de detalhes
        String projectUrl = frontBaseUrl + "/Projetos/" + projeto.getProjectId();

        String qrUrl = "https://api.qrserver.com/v1/create-qr-code/?size=220x220&data="
                + URLEncoder.encode(projectUrl, StandardCharsets.UTF_8);

        projeto.setQrCodeUrl(qrUrl);
        projectRepository.save(projeto);
    }



    public void deletar(UUID id) {
        projectRepository.deleteById(id);
    }

}