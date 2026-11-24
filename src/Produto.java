import java.util.Locale;

/**
 * POJO que representa um produto. Sabe se criar a partir de uma linha do arquivo
 * e se representar em XML.
 */
public class Produto {

    private int codigo;
    private String nome;
    private String categoria;
    private double preco;

    public Produto() {
    }

    public Produto(int codigo, String nome, String categoria, double preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
    }

    /**
     * Converte uma linha no formato "codigo;nome;categoria;preco" em um Produto.
     *
     * @throws IllegalArgumentException se a linha não tiver 4 campos ou se código/preço não forem numéricos
     */
    public static Produto deLinha(String linha, String separador) {
        String[] campos = linha.split(separador, -1);
        if (campos.length != 4) {
            throw new IllegalArgumentException(
                    "esperados 4 campos separados por '" + separador + "', encontrados " + campos.length);
        }
        try {
            int codigo = Integer.parseInt(campos[0].trim());
            String nome = campos[1].trim();
            String categoria = campos[2].trim();
            double preco = Double.parseDouble(campos[3].trim());
            return new Produto(codigo, nome, categoria, preco);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("código ou preço não é um número válido");
        }
    }

    /** Gera o XML do produto, com o recuo informado antes de cada linha. */
    public String toXml(String recuo) {
        String filho = recuo + "  ";
        return recuo + "<produto>\n"
                + filho + "<codigo>" + codigo + "</codigo>\n"
                + filho + "<nome>" + escapar(nome) + "</nome>\n"
                + filho + "<categoria>" + escapar(categoria) + "</categoria>\n"
                + filho + "<preco>" + String.format(Locale.US, "%.2f", preco) + "</preco>\n"
                + recuo + "</produto>";
    }

    public String toXml() {
        return toXml("");
    }

    /** Escapa os caracteres que têm significado especial em XML. */
    private static String escapar(String texto) {
        return texto.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}