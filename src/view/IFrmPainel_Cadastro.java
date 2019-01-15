/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import controller.ValidacaoCpfCnpj;
import controller.Estado;
import controller.Cidade;
import controller.Endereco;
import controller.Nivelacesso;
import controller.Pessoa;
import controller.Usuario;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Iterator;
import javax.swing.JOptionPane;


/**
 *
 * @author Urbanski
 */
public class IFrmPainel_Cadastro extends javax.swing.JInternalFrame {

    /**
     * Creates new form IFrmPainel_Cadastro
     */
    private Pessoa pessoa = null;
    private Usuario usuario = null;
    private Endereco endereco = null;
    private Estado estado = new Estado();
    private Cidade cidade = new Cidade();
    Nivelacesso nivelAcesso = null;
    SimpleDateFormat dataExibicao = new SimpleDateFormat("dd/MM/yyyy");
    
    public IFrmPainel_Cadastro(Usuario usuario) {
        initComponents();
        if(usuario == null){
            carregaEstado();
            carregaNivelAcesso();
        }else{
            Endereco e = new Endereco();
            e = e.retornaEndereco(FrmPrincipal.usuario.getPessoa().getPessoaId());
            JOptionPane.showMessageDialog(this, FrmPrincipal.usuario.getPessoa().getPessoaId());
            desabilitaCampos();
            txtPainel_Cadastro_Nome.setText(FrmPrincipal.usuario.getPessoa().getNome());
            frmtPainel_Cadastro_Cpf.setText(FrmPrincipal.usuario.getCpf());
            frmtPainel_Cadastro_RG.setText(FrmPrincipal.usuario.getRg());
            dtaPainel_Cadastro_DataNasc.setDate(FrmPrincipal.usuario.getDataNasc());
            txtPainel_Cadastr_Email.setText(FrmPrincipal.usuario.getPessoa().getEmail());
            frmtPainel_Cadastro_TelFixo.setText(FrmPrincipal.usuario.getPessoa().getTelFixo());
            frmtPainel_Cadastro_TelCel.setText(FrmPrincipal.usuario.getPessoa().getTelCel());
            txtPainel_Cadastro_Endereco.setText(e.getRua());
            spnPainel_Cadastro_Numero.setValue(e.getNumero());
            txtPainel_Cadastro_Bairro.setText(e.getBairro());
            frmtPainel_Cadastro_Cep.setText(e.getCep());
            //cmbPainel_Cadastro_Estado.setSelectedItem(e.getCidade().getEstado().getUf());
            //cmbPainel_Cadastro_Cidade.setSelectedItem(e.getCidade().getCidade());
            //cmbPainel_Cadastro_NivelAcesso.setSelectedIndex(usuario.getNivelacesso().getNivel());
            txtPainel_Cadastro_Descricao.setText(FrmPrincipal.usuario.getNivelacesso().getDescricao());
            pswPainel_Cadastro_Senha.setVisible(false);
            pswPainel_Cadastro_CSenha.setVisible(false);
            txtPainel_Cadastro_Usuario.setText(FrmPrincipal.usuario.getLogin());
        }
    }
    
    public void limpaCampos(){
        txtPainel_Cadastro_Nome.setText("");
        frmtPainel_Cadastro_Cpf.setText("");
        frmtPainel_Cadastro_RG.setText("");
        dtaPainel_Cadastro_DataNasc.setDate(null);
        txtPainel_Cadastr_Email.setText("");
        frmtPainel_Cadastro_TelFixo.setText("");
        frmtPainel_Cadastro_TelCel.setText("");
        txtPainel_Cadastro_Endereco.setText("");
        spnPainel_Cadastro_Numero.setValue(0);
        txtPainel_Cadastro_Bairro.setText("");
        frmtPainel_Cadastro_Cep.setText("");
        cmbPainel_Cadastro_Estado.setSelectedIndex(0);
        cmbPainel_Cadastro_Cidade.setSelectedIndex(0);
        cmbPainel_Cadastro_NivelAcesso.setSelectedIndex(0);
        txtPainel_Cadastro_Descricao.setText("");
        pswPainel_Cadastro_Senha.setText("");
        pswPainel_Cadastro_CSenha.setText("");
    }
    
    public void desabilitaCampos(){
        txtPainel_Cadastro_Nome.setEditable(false);
        frmtPainel_Cadastro_Cpf.setEditable(false);
        frmtPainel_Cadastro_RG.setEditable(false);
        dtaPainel_Cadastro_DataNasc.setEnabled(false);
        txtPainel_Cadastr_Email.setEditable(false);
        frmtPainel_Cadastro_TelFixo.setEditable(false);
        frmtPainel_Cadastro_TelCel.setEditable(false);
        txtPainel_Cadastro_Endereco.setEditable(false);
        spnPainel_Cadastro_Numero.setEnabled(false);
        txtPainel_Cadastro_Bairro.setEditable(false);
        frmtPainel_Cadastro_Cep.setEditable(false);
        cmbPainel_Cadastro_Estado.setEditable(false);
        cmbPainel_Cadastro_Cidade.setEditable(false);
        pswPainel_Cadastro_Senha.setEditable(false);
        pswPainel_Cadastro_CSenha.setEditable(false);        
    }
    
    public void gerarTeste(){
        txtPainel_Cadastro_Nome.setText("Felipe Urbanski Proença");
        frmtPainel_Cadastro_Cpf.setText("81674656831");
        frmtPainel_Cadastro_RG.setText("426987459");
        dtaPainel_Cadastro_DataNasc.setDate(null);
        txtPainel_Cadastr_Email.setText("teste");
        frmtPainel_Cadastro_TelFixo.setText("1532363236");
        frmtPainel_Cadastro_TelCel.setText("15941526365");
        txtPainel_Cadastro_Endereco.setText("Sophia Dias Menk");
        spnPainel_Cadastro_Numero.setValue(6565);
        txtPainel_Cadastro_Bairro.setText("Centro");
        frmtPainel_Cadastro_Cep.setText("18460000");
        cmbPainel_Cadastro_Estado.setSelectedIndex(0);
        cmbPainel_Cadastro_Cidade.setSelectedIndex(0);
        pswPainel_Cadastro_Senha.setText("123");
        pswPainel_Cadastro_CSenha.setText("123");   
    }
    public void carregaEstado(){
        Iterator i = estado.carregaEstados();
        while(i.hasNext()){
            estado = (Estado)i.next();
            cmbPainel_Cadastro_Estado.addItem(estado.getUf());
        }
        
    }
    public void buscaEstado(String sigla){
        cmbPainel_Cadastro_Cidade.removeAllItems();
        estado = new Estado();
        
        Iterator i = estado.buscaEstado(sigla);
        
        if(i.hasNext()){
            estado = (Estado)i.next();
            carregaCidade(estado.getEstadoId());
        }
    }
    
    public void carregaCidade(int estadoId){
        Iterator i = cidade.carregaCidades(estadoId);
        while(i.hasNext()){
            cidade = (Cidade)i.next();
            cmbPainel_Cadastro_Cidade.addItem(cidade.getNome());
        }
    }
    
    public void atualizaCidade(){
        cidade = new Cidade();
        Iterator i = cidade.carregaCidade(String.valueOf(cmbPainel_Cadastro_Cidade.getSelectedItem()),String.valueOf(cmbPainel_Cadastro_Estado.getSelectedItem()));
        
        if(i.hasNext()){
            cidade = (Cidade)i.next();
        }
    }
    
    public void cadastraPessoa(){
        pessoa = new Pessoa(txtPainel_Cadastro_Nome.getText(),txtPainel_Cadastr_Email.getText(), frmtPainel_Cadastro_TelFixo.getText().replace("(", "").replace(")", "").replace("-", ""), frmtPainel_Cadastro_TelCel.getText().replace("(", "").replace(")", "").replace("-", ""));
        pessoa.cadastraPessoa(pessoa);
    }
    
    public void cadastraEndereco(){
        this.atualizaCidade();
        
        endereco = new Endereco(pessoa, cidade, txtPainel_Cadastro_Endereco.getText(), (int)spnPainel_Cadastro_Numero.getValue(),txtPainel_Cadastro_Bairro.getText(),frmtPainel_Cadastro_Cep.getText().replace("-",""));
        endereco.cadastraEndereco(endereco);
    }   
    
    public void carregaNivelAcesso(){
        nivelAcesso = new Nivelacesso();
        Iterator i = nivelAcesso.retornaNivelAcesso();
        cmbPainel_Cadastro_NivelAcesso.removeAllItems();
        while(i.hasNext()){
            nivelAcesso = (Nivelacesso)i.next();
            cmbPainel_Cadastro_NivelAcesso.addItem(String.valueOf(nivelAcesso.getNivel()));
        }
    }
    
    public void preencheDescricao(int nivel){
        
        Iterator i = nivelAcesso.retornaNivelAcesso(nivel);
        
        if(i.hasNext()){
            nivelAcesso  = (Nivelacesso)i.next();
            txtPainel_Cadastro_Descricao.setText(nivelAcesso.getDescricao());
        }
    }
    
    public void cadastraUsuario(){
        cadastraPessoa();
        cadastraEndereco();
        usuario = new Usuario();
        if(!usuario.validaLogin(txtPainel_Cadastro_Usuario.getText())){
            usuario = new Usuario(nivelAcesso, txtPainel_Cadastro_Usuario.getText(), pswPainel_Cadastro_Senha.getText(), pessoa, (cmbPainel_Cadastro_Sexo.getSelectedIndex() == 0 ? 'M' : 'F' ), new Date(dtaPainel_Cadastro_DataNasc.getDate().getTime()), frmtPainel_Cadastro_Cpf.getText().replace(".", "").replace("-",""), frmtPainel_Cadastro_RG.getText().replace(".", "").replace("-",""));

            if(usuario.cadastraUsuario(usuario)){
                JOptionPane.showMessageDialog(this, "Colaborador "+usuario.getPessoa().getNome()+" cadastrado com sucesso!!","SUCESSO",JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(this, "Colaborador "+usuario.getPessoa().getNome()+" não foi cadastrado..","SUCESSO",JOptionPane.ERROR_MESSAGE);

            }
        }else{
            JOptionPane.showMessageDialog(this, "Já existe um usuario com mesmo login, por favor, informe um novo login.","ATENÇÃO",JOptionPane.ERROR_MESSAGE);
        }
    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtPainel_Cadastro_Nome = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPainel_Cadastr_Email = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        frmtPainel_Cadastro_RG = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        frmtPainel_Cadastro_Cpf = new javax.swing.JFormattedTextField();
        frmtPainel_Cadastro_TelFixo = new javax.swing.JFormattedTextField();
        frmtPainel_Cadastro_TelCel = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmbPainel_Cadastro_Estado = new javax.swing.JComboBox<>();
        cmbPainel_Cadastro_Cidade = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        spnPainel_Cadastro_Numero = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        dtaPainel_Cadastro_DataNasc = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        txtPainel_Cadastro_Bairro = new javax.swing.JTextField();
        txtPainel_Cadastro_Endereco = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        frmtPainel_Cadastro_Cep = new javax.swing.JFormattedTextField();
        jLabel19 = new javax.swing.JLabel();
        cmbPainel_Cadastro_Sexo = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        cmbPainel_Cadastro_NivelAcesso = new javax.swing.JComboBox<>();
        txtPainel_Cadastro_Descricao = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtPainel_Cadastro_Usuario = new javax.swing.JTextField();
        pswPainel_Cadastro_Senha = new javax.swing.JPasswordField();
        pswPainel_Cadastro_CSenha = new javax.swing.JPasswordField();
        btnPainel_Cadastro_Cadastrar = new javax.swing.JButton();
        btnPainel_Cadastro_Cancelar = new javax.swing.JButton();
        btnPainel_Cadastro_Limpar = new javax.swing.JButton();
        btnPainel_Cadastro_Gerar = new javax.swing.JButton();

        setClosable(true);
        setTitle("Novo Colaborador");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Informações Pessoais"));

        jLabel1.setText("Nome Completo");

        jLabel2.setText("E-mail");

        jLabel3.setText("RG");

        try {
            frmtPainel_Cadastro_RG.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###-#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel4.setText("CPF");

        try {
            frmtPainel_Cadastro_Cpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            frmtPainel_Cadastro_TelFixo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            frmtPainel_Cadastro_TelCel.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel6.setText("Tel. Celular");

        jLabel7.setText("Tel. Fixo");

        jLabel5.setText("Estado");

        cmbPainel_Cadastro_Estado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPainel_Cadastro_EstadoActionPerformed(evt);
            }
        });

        jLabel8.setText("Cidade");

        jLabel9.setText("Endereço");

        jLabel10.setText("Número");

        jLabel11.setText("Bairro");

        jLabel12.setText("Data Nascimento");

        jLabel18.setText("CEP");

        try {
            frmtPainel_Cadastro_Cep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel19.setText("Sexo");

        cmbPainel_Cadastro_Sexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Feminino" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(frmtPainel_Cadastro_RG, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(285, 285, 285)
                                .addComponent(jLabel2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(txtPainel_Cadastro_Nome, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 16, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(frmtPainel_Cadastro_Cpf, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtPainel_Cadastr_Email)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(cmbPainel_Cadastro_Estado, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(cmbPainel_Cadastro_Cidade, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(183, 183, 183)
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(dtaPainel_Cadastro_DataNasc, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtPainel_Cadastro_Endereco, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(spnPainel_Cadastro_Numero, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(20, 20, 20)
                                .addComponent(frmtPainel_Cadastro_TelFixo, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(jLabel6)
                                .addGap(20, 20, 20)
                                .addComponent(frmtPainel_Cadastro_TelCel, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel19)
                                .addGap(18, 18, 18)
                                .addComponent(cmbPainel_Cadastro_Sexo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtPainel_Cadastro_Bairro, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(frmtPainel_Cadastro_Cep, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(11, 11, 11))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtPainel_Cadastro_Nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(frmtPainel_Cadastro_Cpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(frmtPainel_Cadastro_RG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12))
                            .addComponent(dtaPainel_Cadastro_DataNasc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtPainel_Cadastr_Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(frmtPainel_Cadastro_TelFixo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(frmtPainel_Cadastro_TelCel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19)
                        .addComponent(cmbPainel_Cadastro_Sexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(txtPainel_Cadastro_Bairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18)
                        .addComponent(frmtPainel_Cadastro_Cep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(spnPainel_Cadastro_Numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(txtPainel_Cadastro_Endereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cmbPainel_Cadastro_Cidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbPainel_Cadastro_Estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(15, 15, 15))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Informações de Acesso"));

        jLabel13.setText("Nivel de Acesso");

        cmbPainel_Cadastro_NivelAcesso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPainel_Cadastro_NivelAcessoActionPerformed(evt);
            }
        });

        txtPainel_Cadastro_Descricao.setEditable(false);

        jLabel14.setText("Descrição");

        jLabel15.setText("Usuário");

        jLabel16.setText("Senha");

        jLabel17.setText("Confirma Senha");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbPainel_Cadastro_NivelAcesso, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(txtPainel_Cadastro_Descricao))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(txtPainel_Cadastro_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(pswPainel_Cadastro_Senha, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(pswPainel_Cadastro_CSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(cmbPainel_Cadastro_NivelAcesso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtPainel_Cadastro_Descricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(pswPainel_Cadastro_CSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(pswPainel_Cadastro_Senha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(txtPainel_Cadastro_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );

        btnPainel_Cadastro_Cadastrar.setText("Cadastrar");
        btnPainel_Cadastro_Cadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPainel_Cadastro_CadastrarActionPerformed(evt);
            }
        });

        btnPainel_Cadastro_Cancelar.setText("Cancelar");
        btnPainel_Cadastro_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPainel_Cadastro_CancelarActionPerformed(evt);
            }
        });

        btnPainel_Cadastro_Limpar.setText("Limpar Campos");
        btnPainel_Cadastro_Limpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPainel_Cadastro_LimparActionPerformed(evt);
            }
        });

        btnPainel_Cadastro_Gerar.setText("Gerar Teste");
        btnPainel_Cadastro_Gerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPainel_Cadastro_GerarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnPainel_Cadastro_Cadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62)
                        .addComponent(btnPainel_Cadastro_Limpar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPainel_Cadastro_Gerar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(btnPainel_Cadastro_Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPainel_Cadastro_Cadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPainel_Cadastro_Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPainel_Cadastro_Limpar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPainel_Cadastro_Gerar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbPainel_Cadastro_EstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPainel_Cadastro_EstadoActionPerformed
        buscaEstado(cmbPainel_Cadastro_Estado.getSelectedItem().toString());
    }//GEN-LAST:event_cmbPainel_Cadastro_EstadoActionPerformed

    private void btnPainel_Cadastro_LimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPainel_Cadastro_LimparActionPerformed
        limpaCampos();
    }//GEN-LAST:event_btnPainel_Cadastro_LimparActionPerformed

    private void btnPainel_Cadastro_GerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPainel_Cadastro_GerarActionPerformed
        gerarTeste();
    }//GEN-LAST:event_btnPainel_Cadastro_GerarActionPerformed

    private void btnPainel_Cadastro_CadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPainel_Cadastro_CadastrarActionPerformed
        if(ValidacaoCpfCnpj.isValidCPF(frmtPainel_Cadastro_Cpf.getText().replace(".", "").replace("-", "")) && usuario.verificaCpf(frmtPainel_Cadastro_Cpf.getText().replace(".", "").replace("-", ""))){
            if(pswPainel_Cadastro_Senha.getText().equals(pswPainel_Cadastro_CSenha.getText())){
                 cadastraUsuario();
                 IFrmPainel.popularTabelaUsuario(null, -1);
                 dispose();
            }else{
                JOptionPane.showMessageDialog(this, "As senhas não são iguais, favor, revisar esse campo..","ATENÇÃO",JOptionPane.ERROR_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(this, "CPF informado não é valido ou já está cadastrado","ATENÇÃO",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnPainel_Cadastro_CadastrarActionPerformed

    private void cmbPainel_Cadastro_NivelAcessoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPainel_Cadastro_NivelAcessoActionPerformed
        preencheDescricao(Integer.valueOf(cmbPainel_Cadastro_NivelAcesso.getSelectedItem().toString()));
    }//GEN-LAST:event_cmbPainel_Cadastro_NivelAcessoActionPerformed

    private void btnPainel_Cadastro_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPainel_Cadastro_CancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnPainel_Cadastro_CancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPainel_Cadastro_Cadastrar;
    private javax.swing.JButton btnPainel_Cadastro_Cancelar;
    private javax.swing.JButton btnPainel_Cadastro_Gerar;
    private javax.swing.JButton btnPainel_Cadastro_Limpar;
    private javax.swing.JComboBox<String> cmbPainel_Cadastro_Cidade;
    private javax.swing.JComboBox<String> cmbPainel_Cadastro_Estado;
    private javax.swing.JComboBox<String> cmbPainel_Cadastro_NivelAcesso;
    private javax.swing.JComboBox<String> cmbPainel_Cadastro_Sexo;
    private com.toedter.calendar.JDateChooser dtaPainel_Cadastro_DataNasc;
    private javax.swing.JFormattedTextField frmtPainel_Cadastro_Cep;
    private javax.swing.JFormattedTextField frmtPainel_Cadastro_Cpf;
    private javax.swing.JFormattedTextField frmtPainel_Cadastro_RG;
    private javax.swing.JFormattedTextField frmtPainel_Cadastro_TelCel;
    private javax.swing.JFormattedTextField frmtPainel_Cadastro_TelFixo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField pswPainel_Cadastro_CSenha;
    private javax.swing.JPasswordField pswPainel_Cadastro_Senha;
    private javax.swing.JSpinner spnPainel_Cadastro_Numero;
    private javax.swing.JTextField txtPainel_Cadastr_Email;
    private javax.swing.JTextField txtPainel_Cadastro_Bairro;
    private javax.swing.JTextField txtPainel_Cadastro_Descricao;
    private javax.swing.JTextField txtPainel_Cadastro_Endereco;
    private javax.swing.JTextField txtPainel_Cadastro_Nome;
    private javax.swing.JTextField txtPainel_Cadastro_Usuario;
    // End of variables declaration//GEN-END:variables
}
