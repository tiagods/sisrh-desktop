public class ValidacaoExcel {
    public static void main(String[] args) {
        String[] cabecalho = new String[] {"Id","Modificado Em","Criado por","Nascimento","Nome",
                "Curriculo","Escolaridade","Sexo","Estado Civil","Filhos","Qtde Filhos","Fumante",
                "Nacionalidade","Indicacao","Empresa Indicacao","Det.Indicacao","Telefone","Celular",
                "Email","UF","Cidade","Objetivo 1","Objetivo 2","Objetivo 3","Disponivel",
                "Obs.Disponivel","Empresa 1", "Cargo 1","Empresa 2", "Cargo 2","Empresa 3", "Cargo 3"};

        Integer[] colunasLenght = new Integer[] {
                10, 20,20,20,20,
                10,20,5,5,5,5,5,
                20,5,20,30,20,20,
                20,5,10,10,10,10,5,
                20,20,20,20,20,20,20
        };


        System.out.println(cabecalho.length+"-"+colunasLenght.length);
    }
}
