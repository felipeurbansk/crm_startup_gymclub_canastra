/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import org.hibernate.Session;
import org.hibernate.Query;

import dao.DAOSistema;
import controller.Fornecedor;
import controller.Endereco;
import controller.Cidade;
import controller.Estado;
import controller.Pessoa;
import controller.ValidacaoCpfCnpj;

import java.util.Iterator;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static view.IFrmFinanceiro_Fornecedor_Visualizar.fornecedorId;


/**
 *
 * @author Urbanski
 */
public class IFrmFinanceiro_Fornecedor_Cadastro extends javax.swing.JInternalFrame {

    /**
     * Creates new form IFrmFinanceiro_Fornecedor_Cadastro
     */
    /*
        Objetos Instanciados
    */
    private Fornecedor fornecedor = null;
    private Endereco endereco = null;
    private Pessoa pessoa = null;
    private Cidade cidade = null;
    
    /*
        Variaveis
    */
    public int id =-1;
    
    //Limpa todos os campos da interface IFrmFinanceiro_Fornecedor_Cadastro
    private void limparCampos(){
        txtAdicionarFornecedor_RazaoSocial.setText("");
        txtAdicionarFornecedor_NomeFantasia.setText("");
        frmtAdicionarFornecedor_Cnpj.setText("");
        frmtAdicionarFornecedor_TelFixo.setText("");
        frmtAdicionarFornecedor_TelCel.setText("");
        txtAdicionarFornecedor_Endereco.setText("");
        cmbAdicionarFornecedor_Cidade.setSelectedIndex(0);
        cmbAdicionarFornecedor_Estado.setSelectedIndex(0);
        spnAdicionarFornecedor_Numero.setValue(0);
        txtAdicionarFornecedor_Email.setText("");
        txtAdicionarFornecedor_Ramo.setText("");
        frmtAdicionarFornecedor_Cep.setText("");
    }
    
    //Carrega o campo Estado
    private void carregaEstado(){
        
        Estado estado = new Estado();
        Iterator i = estado.carregaEstados();

        while(i.hasNext()){
            estado = (Estado)i.next();
            cmbAdicionarFornecedor_Estado.addItem(estado.getUf());
        }
    }
    /*
        Busca o endereco pela sigla endereco envia para o metodo CarregaCmbCidade 
        o id do estado assim retornando para a interface as cidades do respectivo endereco.
    */
    private void buscaEstado(String sigla){
        cmbAdicionarFornecedor_Cidade.removeAllItems();
        Estado e = new Estado();
        Iterator i = e.buscaEstado(sigla);
        if(i.hasNext()){
            e = (Estado)i.next();
            carregaCidade(e.getEstadoId());
        }
    }
    
    
    //Carrega o campo Cidade com cidades do endereco selecionado
    private void carregaCidade(int estadoId){

        Cidade c = new Cidade();
        Iterator i = c.carregaCidades(estadoId);
        
        while(i.hasNext()){
            c = (Cidade)i.next();
            cmbAdicionarFornecedor_Cidade.addItem(c.getNome());
        }

    }
    /*
        Deixa o objeto cidade instanciado no inicio da aplicação
    
    */
    private void carregaCidade(){
        Cidade c = new Cidade();
        Iterator i = c.carregaCidade(String.valueOf(cmbAdicionarFornecedor_Cidade.getSelectedItem()),String.valueOf(cmbAdicionarFornecedor_Estado.getSelectedItem()));
        if(i.hasNext()){
            cidade = (Cidade)i.next();
        }
    }
    
     private void carregarFornecedor(){
        fornecedor = new Fornecedor();
        fornecedor = fornecedor.retornaFornecedor(fornecedorId);
        txtAdicionarFornecedor_RazaoSocial.setText(fornecedor.getRazao());
        txtAdicionarFornecedor_NomeFantasia.setText(fornecedor.getPessoa().getNome());
        frmtAdicionarFornecedor_Cnpj.setText(fornecedor.getCnpj().replace(".", "").replace("-", "").replace("/", ""));
        frmtAdicionarFornecedor_TelFixo.setText(fornecedor.getPessoa().getTelFixo());
        frmtAdicionarFornecedor_TelCel.setText(fornecedor.getPessoa().getTelCel());
        txtAdicionarFornecedor_Email.setText(fornecedor.getPessoa().getEmail());
        txtAdicionarFornecedor_Ramo.setText(fornecedor.getRamo());

    }
      
    private void carregaEndereco(){
            endereco = new Endereco();
            endereco = endereco.retornaEndereco(fornecedor.getPessoa().getPessoaId());
            txtAdicionarFornecedor_Bairro.setText(endereco.getBairro());
            txtAdicionarFornecedor_Endereco.setText(endereco.getRua());
            spnAdicionarFornecedor_Numero.setValue(endereco.getNumero());
            frmtAdicionarFornecedor_Cep.setText(endereco.getCep());
    }

    
    private void cadastraPessoa(){
        pessoa = new Pessoa(txtAdicionarFornecedor_NomeFantasia.getText(), txtAdicionarFornecedor_Email.getText(), frmtAdicionarFornecedor_TelFixo.getText(), frmtAdicionarFornecedor_TelCel.getText());
        
        pessoa.cadastraPessoa(pessoa);

    }
    private void alteraPessoa(){
        pessoa = new Pessoa(txtAdicionarFornecedor_NomeFantasia.getText(), txtAdicionarFornecedor_Email.getText(), frmtAdicionarFornecedor_TelFixo.getText(), frmtAdicionarFornecedor_TelCel.getText());
        
        pessoa.alteraPessoa(pessoa);
    }
    
    private void cadastraEndereco(){
       this.carregaCidade();
       endereco = new Endereco(pessoa,cidade,txtAdicionarFornecedor_Endereco.getText(), (int)spnAdicionarFornecedor_Numero.getValue(), txtAdicionarFornecedor_Bairro.getText(), frmtAdicionarFornecedor_Cep.getText().replace("-", ""));
       endereco.cadastraEndereco(endereco);
    }

    public IFrmFinanceiro_Fornecedor_Cadastro(Fornecedor fornecedor) {
        initComponents();
        // Caso não seja nulo então trata-se de uma alteração
        if(fornecedor != null){
            this.fornecedor = fornecedor;
            btnAdicionarFornecedor_AdicionarAlterar.setText("Alterar");
            this.setTitle("Alterar Fornecedor("+this.fornecedor.getPessoa().getNome()+")");
            btnAdicionarFornecedor_Limpar.setVisible(false);
            btnAdicionarFornecedor_GeraTeste.setVisible(false);
            ImageIcon icone = new ImageIcon("src/icon/008-pencil-1.png");
            btnAdicionarFornecedor_AdicionarAlterar.setIcon(icone);
            btnAdicionarFornecedor_AdicionarAlterar.setSize(70, 34);
            carregarFornecedor();
            carregaEndereco();
        }
        carregaEstado();
    }   
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlAdicionarFornecedor = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtAdicionarFornecedor_RazaoSocial = new javax.swing.JTextField();
        txtAdicionarFornecedor_NomeFantasia = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        frmtAdicionarFornecedor_Cnpj = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtAdicionarFornecedor_Endereco = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        frmtAdicionarFornecedor_Cep = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        cmbAdicionarFornecedor_Cidade = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtAdicionarFornecedor_Email = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtAdicionarFornecedor_Ramo = new javax.swing.JTextField();
        btnAdicionarFornecedor_AdicionarAlterar = new javax.swing.JButton();
        btnAdicionarFornecedor_Limpar = new javax.swing.JButton();
        btnAdicionarFornecedor_Cancelar = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtAdicionarFornecedor_Bairro = new javax.swing.JTextField();
        spnAdicionarFornecedor_Numero = new javax.swing.JSpinner();
        cmbAdicionarFornecedor_Estado = new javax.swing.JComboBox<>();
        btnAdicionarFornecedor_GeraTeste = new javax.swing.JButton();
        frmtAdicionarFornecedor_TelFixo = new javax.swing.JFormattedTextField();
        frmtAdicionarFornecedor_TelCel = new javax.swing.JFormattedTextField();

        setClosable(true);
        setTitle("Adicionar Fornecedor");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        pnlAdicionarFornecedor.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cadastrar Fornecedor"));

        jLabel1.setText("Razão Social");

        jLabel2.setText("Nome Fantasia");

        jLabel3.setText("CNPJ");

        try {
            frmtAdicionarFornecedor_Cnpj.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###/####-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel4.setText("Tel. Fixo");

        jLabel5.setText("Tel. Celular");

        jLabel6.setText("Endereço");

        jLabel7.setText("Número");

        jLabel8.setText("CEP");

        try {
            frmtAdicionarFornecedor_Cep.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel9.setText("Cidade");

        cmbAdicionarFornecedor_Cidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAdicionarFornecedor_CidadeActionPerformed(evt);
            }
        });

        jLabel10.setText("Estado");

        jLabel11.setText("E-mail");

        jLabel12.setText("Ramo");

        btnAdicionarFornecedor_AdicionarAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/png/006-social.png"))); // NOI18N
        btnAdicionarFornecedor_AdicionarAlterar.setText("Adicionar Fornecedor");
        btnAdicionarFornecedor_AdicionarAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarFornecedor_AdicionarAlterarActionPerformed(evt);
            }
        });

        btnAdicionarFornecedor_Limpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/two-circling-arrows.png"))); // NOI18N
        btnAdicionarFornecedor_Limpar.setText("Limpar");
        btnAdicionarFornecedor_Limpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarFornecedor_LimparActionPerformed(evt);
            }
        });

        btnAdicionarFornecedor_Cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/002-cancel-1.png"))); // NOI18N
        btnAdicionarFornecedor_Cancelar.setText("Cancelar");
        btnAdicionarFornecedor_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarFornecedor_CancelarActionPerformed(evt);
            }
        });

        jLabel13.setText("Bairro");

        txtAdicionarFornecedor_Bairro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAdicionarFornecedor_BairroActionPerformed(evt);
            }
        });

        cmbAdicionarFornecedor_Estado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAdicionarFornecedor_EstadoActionPerformed(evt);
            }
        });

        btnAdicionarFornecedor_GeraTeste.setText("gera teste");
        btnAdicionarFornecedor_GeraTeste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarFornecedor_GeraTesteActionPerformed(evt);
            }
        });

        try {
            frmtAdicionarFornecedor_TelFixo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            frmtAdicionarFornecedor_TelCel.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout pnlAdicionarFornecedorLayout = new javax.swing.GroupLayout(pnlAdicionarFornecedor);
        pnlAdicionarFornecedor.setLayout(pnlAdicionarFornecedorLayout);
        pnlAdicionarFornecedorLayout.setHorizontalGroup(
            pnlAdicionarFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAdicionarFornecedorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAdicionarFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAdicionarFornecedorLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(15, 15, 15)
                        .addComponent(txtAdicionarFornecedor_RazaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(15, 15, 15)
                        .addComponent(txtAdicionarFornecedor_NomeFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlAdicionarFornecedorLayout.createSequentialGroup()
                        .addGroup(pnlAdicionarFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlAdicionarFornecedorLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(15, 15, 15)
                                .addGroup(pnlAdicionarFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtAdicionarFornecedor_Bairro, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pnlAdicionarFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(pnlAdicionarFornecedorLayout.createSequentialGroup()
                                            .addComponent(jLabel4)
                                            .addGap(18, 18, 18)
                                            .addComponent(frmtAdicionarFornecedor_TelFixo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(pnlAdicionarFornecedorLayout.createSequentialGroup()
                                            .addComponent(txtAdicionarFornecedor_Endereco, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(jLabel7)))))
                            .addGroup(pnlAdicionarFornecedorLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(15, 15, 15)
                                .addComponent(frmtAdicionarFornecedor_Cnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(pnlAdicionarFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlAdicionarFornecedorLayout.createSequentialGroup()
                                .addComponent(spnAdicionarFornecedor_Numero)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(frmtAdicionarFornecedor_Cep, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12))
                            .addGroup(pnlAdicionarFornecedorLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(frmtAdicionarFornecedor_TelCel))))
                    .addGroup(pnlAdicionarFornecedorLayout.createSequentialGroup()
                        .addGroup(pnlAdicionarFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAdicionarFornecedorLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(txtAdicionarFornecedor_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(46, 46, 46)
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(txtAdicionarFornecedor_Ramo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAdicionarFornecedorLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbAdicionarFornecedor_Estado, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jLabel9))
                            .addGroup(pnlAdicionarFornecedorLayout.createSequentialGroup()
                                .addComponent(btnAdicionarFornecedor_AdicionarAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(93, 93, 93)
                                .addComponent(btnAdicionarFornecedor_Limpar, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(pnlAdicionarFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAdicionarFornecedor_GeraTeste, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbAdicionarFornecedor_Cidade, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAdicionarFornecedor_Cancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        pnlAdicionarFornecedorLayout.setVerticalGroup(
            pnlAdicionarFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAdicionarFornecedorLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlAdicionarFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtAdicionarFornecedor_RazaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtAdicionarFornecedor_NomeFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlAdicionarFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(frmtAdicionarFornecedor_Cnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(frmtAdicionarFornecedor_TelFixo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(frmtAdicionarFornecedor_TelCel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlAdicionarFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtAdicionarFornecedor_Endereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(frmtAdicionarFornecedor_Cep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnAdicionarFornecedor_Numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlAdicionarFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cmbAdicionarFornecedor_Cidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(cmbAdicionarFornecedor_Estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtAdicionarFornecedor_Bairro, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlAdicionarFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAdicionarFornecedor_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtAdicionarFornecedor_Ramo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(btnAdicionarFornecedor_GeraTeste))
                .addGap(30, 30, 30)
                .addGroup(pnlAdicionarFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAdicionarFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAdicionarFornecedor_AdicionarAlterar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAdicionarFornecedor_Limpar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnAdicionarFornecedor_Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(pnlAdicionarFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(pnlAdicionarFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        dispose();
        IFrmFinanceiro.atualizaTblFornecedor();
    }//GEN-LAST:event_formInternalFrameClosing

    private void btnAdicionarFornecedor_LimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarFornecedor_LimparActionPerformed
        limparCampos();
    }//GEN-LAST:event_btnAdicionarFornecedor_LimparActionPerformed

    private void btnAdicionarFornecedor_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarFornecedor_CancelarActionPerformed
        dispose();
        IFrmFinanceiro.atualizaTblFornecedor();
    }//GEN-LAST:event_btnAdicionarFornecedor_CancelarActionPerformed

    
    private void btnAdicionarFornecedor_AdicionarAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarFornecedor_AdicionarAlterarActionPerformed

        //Se o texto que tiver no botão foi diferente de Alterar então trata-se de um novo cadastro
        if(ValidacaoCpfCnpj.isValidCNPJ(frmtAdicionarFornecedor_Cnpj.getText().replace(".", "").replace("/", "").replace("-", "")) && fornecedor.verificaCnpj(frmtAdicionarFornecedor_Cnpj.getText().replace(".", "").replace("/", "").replace("-", ""))){
            if(!btnAdicionarFornecedor_AdicionarAlterar.getText().equals("Alterar")){
                //Cadastro
                this.cadastraPessoa();
                this.cadastraEndereco();
                fornecedor = new Fornecedor(pessoa, txtAdicionarFornecedor_RazaoSocial.getText(), frmtAdicionarFornecedor_Cnpj.getText(), txtAdicionarFornecedor_Ramo.getText());
                fornecedor.cadastraFornecedor(fornecedor);
                JOptionPane.showMessageDialog(this, "Fornecedor cadastrado com sucesso!!","SUCESSO",JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                IFrmFinanceiro.atualizaAllTbl();
                dispose();
            }else{
               //Atualizar
               fornecedor.getPessoa().alteraPessoa(fornecedor.getPessoa());
               try{
                   Session s = new DAOSistema().getSessionFactory().openSession();
                   s.beginTransaction();
                   s.merge(fornecedor);
                   s.getTransaction().commit();
                   IFrmFinanceiro.atualizaTblFornecedor();
               }catch(Exception ex){
                   ex.printStackTrace();
                   JOptionPane.showMessageDialog(this, "Não foi possivel alterar o fornecedor.","ATENÇÃO",JOptionPane.ERROR_MESSAGE);
               }

           }
        }else{
            JOptionPane.showMessageDialog(this, "CNPJ informado não é valido ou já está cadastrado","ATENÇÃO",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAdicionarFornecedor_AdicionarAlterarActionPerformed

    private void cmbAdicionarFornecedor_EstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAdicionarFornecedor_EstadoActionPerformed
        buscaEstado(String.valueOf(cmbAdicionarFornecedor_Estado.getSelectedItem()));
    }//GEN-LAST:event_cmbAdicionarFornecedor_EstadoActionPerformed

    private void btnAdicionarFornecedor_GeraTesteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarFornecedor_GeraTesteActionPerformed
        txtAdicionarFornecedor_RazaoSocial.setText("Marilene corp");
        txtAdicionarFornecedor_NomeFantasia.setText("Marilene");
        frmtAdicionarFornecedor_Cnpj.setText("98345475000114");
        frmtAdicionarFornecedor_TelFixo.setText("1535325454");
        frmtAdicionarFornecedor_TelCel.setText("15986745123");
        txtAdicionarFornecedor_Endereco.setText("Pedro Dias Tatit");
        cmbAdicionarFornecedor_Cidade.setSelectedIndex(0);
        cmbAdicionarFornecedor_Estado.setSelectedIndex(0);
        spnAdicionarFornecedor_Numero.setValue(85);
        txtAdicionarFornecedor_Ramo.setText("Tecnologia");
        frmtAdicionarFornecedor_Cep.setText("18460000");
        txtAdicionarFornecedor_Bairro.setText("Centro");
        txtAdicionarFornecedor_Email.setText("marilene@tainha.com");
    }//GEN-LAST:event_btnAdicionarFornecedor_GeraTesteActionPerformed

    private void txtAdicionarFornecedor_BairroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAdicionarFornecedor_BairroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAdicionarFornecedor_BairroActionPerformed

    private void cmbAdicionarFornecedor_CidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAdicionarFornecedor_CidadeActionPerformed
        
    }//GEN-LAST:event_cmbAdicionarFornecedor_CidadeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionarFornecedor_AdicionarAlterar;
    private javax.swing.JButton btnAdicionarFornecedor_Cancelar;
    private javax.swing.JButton btnAdicionarFornecedor_GeraTeste;
    private javax.swing.JButton btnAdicionarFornecedor_Limpar;
    private javax.swing.JComboBox<String> cmbAdicionarFornecedor_Cidade;
    private javax.swing.JComboBox<String> cmbAdicionarFornecedor_Estado;
    private javax.swing.JFormattedTextField frmtAdicionarFornecedor_Cep;
    private javax.swing.JFormattedTextField frmtAdicionarFornecedor_Cnpj;
    private javax.swing.JFormattedTextField frmtAdicionarFornecedor_TelCel;
    private javax.swing.JFormattedTextField frmtAdicionarFornecedor_TelFixo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel pnlAdicionarFornecedor;
    private javax.swing.JSpinner spnAdicionarFornecedor_Numero;
    private javax.swing.JTextField txtAdicionarFornecedor_Bairro;
    private javax.swing.JTextField txtAdicionarFornecedor_Email;
    private javax.swing.JTextField txtAdicionarFornecedor_Endereco;
    private javax.swing.JTextField txtAdicionarFornecedor_NomeFantasia;
    private javax.swing.JTextField txtAdicionarFornecedor_Ramo;
    private javax.swing.JTextField txtAdicionarFornecedor_RazaoSocial;
    // End of variables declaration//GEN-END:variables
}
