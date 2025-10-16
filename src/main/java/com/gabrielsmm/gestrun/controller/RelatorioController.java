package com.gabrielsmm.gestrun.controller;

import com.gabrielsmm.gestrun.service.RelatorioResultadosService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/relatorios")
@RequiredArgsConstructor
@Tag(name = "7. Relatórios", description = "Geração de relatórios")
@SecurityRequirement(name = "Bearer Authentication")
public class RelatorioController {

    private final RelatorioResultadosService relatorioResultadosService;

    @GetMapping("/resultados/geral")
    public ResponseEntity<byte[]> gerarResultadosGeral(
            @RequestParam(name = "corridaId") Long corridaId,
            @RequestParam(name = "formato", defaultValue = "pdf") String formato
    ) throws JRException {
        byte[] relatorio = relatorioResultadosService.gerarResultadosGeral(corridaId, formato);
        return gerarResponse(relatorio, formato, "resultado_geral");
    }

    private ResponseEntity<byte[]> gerarResponse(byte[] relatorio, String formato, String nomeArquivo) {
        String contentType = switch (formato.toLowerCase()) {
            case "pdf" -> MediaType.APPLICATION_PDF_VALUE;
            case "xlsx" -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            default -> MediaType.APPLICATION_OCTET_STREAM_VALUE;
        };
        String fileExtension = "pdf".equalsIgnoreCase(formato) ? "pdf" : "xlsx";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nomeArquivo + "." + fileExtension);
        return new ResponseEntity<>(relatorio, headers, HttpStatus.OK);
    }

}
