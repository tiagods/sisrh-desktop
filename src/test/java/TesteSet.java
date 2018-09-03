import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class TesteSet {
    public static void main(String[] args) {
        Set<Lista> list = new LinkedHashSet<>();
        list.add(new Lista("Valor 1", "Detalhes1"));

        Optional<Lista> result = list.stream().filter(c->c.getNome().equals("Valor 1")).findAny();
        if(result.isPresent()){
            Lista lista = result.get();
            lista.setDetalhes("111111111");
        }
        System.out.println(list);
    }

    public static class Lista{

        public Lista(String nome, String detalhes){
            this.nome=nome;
            this.detalhes=detalhes;
        }
        public Lista(String detalhes){
            this.detalhes=detalhes;
        }

        String nome;
        String detalhes;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getDetalhes() {
            return detalhes;
        }

        public void setDetalhes(String detalhes) {
            this.detalhes = detalhes;
        }

        @Override
        public String toString() {
            return this.nome+"\t"+this.detalhes;
        }
    }
}
