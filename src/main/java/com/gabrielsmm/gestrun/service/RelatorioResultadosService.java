package com.gabrielsmm.gestrun.service;

import com.gabrielsmm.gestrun.dto.ResultadoRelatorioDTO;
import com.gabrielsmm.gestrun.exception.RelatorioSemDadosException;
import com.gabrielsmm.gestrun.repository.RelatorioResultadosRepository;
import com.gabrielsmm.gestrun.util.RelatorioUtil;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class RelatorioResultadosService {

    private static final String CAMINHO_RELATORIOS = "reports/";
    private final RelatorioResultadosRepository relatorioResultadosRepository;
    private final CorridaService corridaService;
    private final CategoriaService categoriaService;

    public byte[] gerarResultadosGeral(Long corridaId, String formato) throws JRException {
        String nomeCorrida = buscarNomeCorrida(corridaId);
        List<ResultadoRelatorioDTO> resultados = relatorioResultadosRepository.buscarResultadoGeralPorCorrida(corridaId);

        if (resultados.isEmpty()) {
            throw new RelatorioSemDadosException("Nenhum resultado encontrado para a corrida: " + nomeCorrida);
        }

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(resultados);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO", nomeCorrida);
        parametros.put("DESCRICAO", "Resultado Geral");

        try (InputStream jrxmlStream = new ClassPathResource(CAMINHO_RELATORIOS + "relatorio_resultados_geral.jrxml").getInputStream()) {
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
            return RelatorioUtil.exportarRelatorio(jasperPrint, formato);
        } catch (IOException | JRException e) {
            throw new JRException("Erro ao gerar relatório: " + e.getMessage(), e);
        }
    }

    public byte[] gerarResultadosPorCategoria(Long corridaId, Long categoriaId, String formato) throws JRException {
        String nomeCorrida = buscarNomeCorrida(corridaId);
        String nomeCategoria = buscarNomeCategoria(categoriaId);
        List<ResultadoRelatorioDTO> resultados = relatorioResultadosRepository.buscarResultadoPorCorridaEPorCategoria(corridaId, categoriaId);

        if (resultados.isEmpty()) {
            throw new RelatorioSemDadosException("Nenhum resultado encontrado para a corrida " + nomeCorrida + " na categoria ID " + categoriaId);
        }

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(resultados);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("TITULO", nomeCorrida);
        parametros.put("DESCRICAO", "Resultado por Categoria");
        parametros.put("CATEGORIA", nomeCategoria);

        try (InputStream jrxmlStream = new ClassPathResource(CAMINHO_RELATORIOS + "relatorio_resultados_categoria.jrxml").getInputStream()) {
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);
            return RelatorioUtil.exportarRelatorio(jasperPrint, formato);
        } catch (IOException | JRException e) {
            throw new JRException("Erro ao gerar relatório: " + e.getMessage(), e);
        }
    }

    private String buscarNomeCorrida(Long corridaId) {
        return corridaService.buscarPorId(corridaId).getNome();
    }

    private String buscarNomeCategoria(Long categoriaId) {
        return categoriaService.buscarPorId(categoriaId).getNome();
    }

}
