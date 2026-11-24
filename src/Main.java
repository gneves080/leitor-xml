import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String SEPARADOR = ";";

    public static void main(String[] args) {
        Path entrada = Paths.get(args.length > 0 ? args[0] : "produtos.txt");
        Path saida = Paths.get("produtos.xml");

        List<Produto> produtos = new ArrayList<>();

        // Leitura linha a linha, aplicando a regra de separação (ponto e vírgula)
        try (BufferedReader leitor = Files.newBufferedReader(entrada, StandardCharsets.UTF_8)) {
            String linha;
            int numeroLinha = 0;
            while ((linha = leitor.readLine()) != null) {
                numeroLinha++;
                if (linha.trim().isEmpty()) {
                    continue;
                }
                try {
                    produtos.add(Produto.deLinha(linha, SEPARADOR));
                } catch (IllegalArgumentException e) {
                    System.out.println("Linha " + numeroLinha + " ignorada: " + e.getMessage());
                }
            }
        } catch (NoSuchFileException e) {
            System.out.println("Arquivo não encontrado: " + entrada);
            return;
        } catch (IOException e) {
            System.out.println("Não foi possível ler o arquivo " + entrada + ": " + e.getMessage());
            return;
        }

        System.out.println(produtos.size() + " produto(s) lido(s) de " + entrada + ":\n");

        // Exibição de cada objeto em XML
        for (Produto produto : produtos) {
            System.out.println(produto.toXml());
        }

        // Também grava um documento XML completo, com elemento raiz
        StringBuilder documento = new StringBuilder();
        documento.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<produtos>\n");
        for (Produto produto : produtos) {
            documento.append(produto.toXml("  ")).append("\n");
        }
        documento.append("</produtos>\n");

        try {
            Files.write(saida, documento.toString().getBytes(StandardCharsets.UTF_8));
            System.out.println("\nDocumento XML completo gravado em " + saida + ".");
        } catch (IOException e) {
            System.out.println("Não foi possível gravar " + saida + ": " + e.getMessage());
        }
    }
}