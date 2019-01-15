/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
/*
    Controllers
*/
import controller.Caixa;
import controller.Fornecedor;
import controller.Contapagar;
import controller.Contareceber;
import controller.Situacao;
import controller.Transacao;
import controller.Usuario;
/*
    Formularios
*/
import static view.FrmPrincipal.dskPrincipal;
/*
    Outros Componentes
*/
import java.util.Calendar;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Urbanski
 */
public class IFrmFinanceiro extends javax.swing.JInternalFrame {

    
    /**
     * Instancias de Views
     */
    
    
    /*
        Fim instancia de Formularios
    */
    
    
    /*
        Instancia de Controllers
    */
        static Fornecedor fornecedor = new Fornecedor();
        Caixa caixa = new Caixa();
        Usuario usuario = new Usuario();
        Situacao situacao = new Situacao();
        Transacao transacao = new Transacao();
        Contapagar contaPagar = new Contapagar();
        Contareceber contaReceber = new Contareceber();
        
    /*
        Fim Instancia de Controllers
    */
    
        
    /*
        Outros metodos ou variaveis
    */
        static DefaultTableModel dtmFornecedor;
        static DefaultTableModel dtmContapagar_AVencer;
        static DefaultTableModel dtmContapagar_Vencida;
        static DefaultTableModel dtmContapagar_Pago;
        static DefaultTableModel dtmContareceber_AVencer;
        static DefaultTableModel dtmContareceber_Vencido;
        static DefaultTableModel dtmContareceber_Recebido;
        static DefaultTableModel dtmCaixa;
        
        SimpleDateFormat dataHoje = new SimpleDateFormat("dd/MM/yyyy");
        int id = -1;
        static float saldoInicial,saldoFinal,saldoAtual,totalEntrada,totalSaida;
    /*
        Fim Outros metodos
    */    
        
    /*
        Metodos Gerais
    */

    public static void carregaTabela(String valor, int filtro, int especificacao){
        /*
            int especificacao = Especifica qual tabela
            sera atualizada ex: tabela f, tabela cp, etc...
        */
        Iterator i = null;
        Fornecedor f = new Fornecedor();
        Contapagar cp = new Contapagar();
        Contareceber cr = new Contareceber();
        
        switch(especificacao){
            
            case 1:
                // Popula tabela Fornecedor
                dtmFornecedor = (DefaultTableModel)tblFinanceiro_Fornecedor.getModel();
                dtmFornecedor.setNumRows(0);
            
                
 
                i = f.retornaFornecedores(valor, filtro);
            
                while(i.hasNext()){
                    f = (Fornecedor)i.next();
                    dtmFornecedor.addRow(f.getFornecedor());
                }
                
                break;
                
            case 2:
                // Popula Contas a Pagar não vencidas
                dtmContapagar_AVencer = (DefaultTableModel)tblContaPagar_AVencer.getModel();
                dtmContapagar_AVencer.setNumRows(0);

                i = cp.retornaContaPagar(valor, filtro, 0);
                
                while(i.hasNext()){
                    cp = (Contapagar)i.next();
                    dtmContapagar_AVencer.addRow(cp.getContaPagar());
                }
                break;
                
            case 3:
                // Popula Contas a pagar Vencidas
                dtmContapagar_Vencida = null;
                dtmContapagar_Vencida = (DefaultTableModel)tblContaPagar_Vencido.getModel();
                dtmContapagar_Vencida.setNumRows(0);
                

                i = cp.retornaContaPagar(valor,filtro,1);
                
                while(i.hasNext()){
                    cp = (Contapagar)i.next();
                    dtmContapagar_Vencida.addRow(cp.getContaPagar());
                }
                break;
                
            case 4:
                // Popula Contas a pagar Pagas
                dtmContapagar_Pago = null;
                dtmContapagar_Pago = (DefaultTableModel)tblContaPagar_Pago.getModel();
                dtmContapagar_Pago.setNumRows(0);
                

                i = cp.retornaContaPagar(valor,filtro,2);
                
                
                while(i.hasNext()){
                    cp = (Contapagar)i.next();
                    dtmContapagar_Pago.addRow(cp.getContaPagarPago());
                }
                break;
                
            case 5:
                if(FrmPrincipal.caixa != null){
                    dtmCaixa = null;
                    dtmCaixa = (DefaultTableModel)tblCaixa_Transacao.getModel();
                    dtmCaixa.setNumRows(0);


                    i = cp.retornaContaPagarCaixa(FrmPrincipal.caixa.getCaixaId());

                    while(i.hasNext()){
                        cp = (Contapagar)i.next();
                        dtmCaixa.addRow(cp.getContaPagar_Caixa());
                        IFrmFinanceiro.totalSaida += cp.getTotal();
                        IFrmFinanceiro.atualizaCaixa();
                    }
                }
                break;
            /*
                Conta receber
            */
            case 6:
                dtmContareceber_AVencer = null;
                dtmContareceber_AVencer = (DefaultTableModel)tblContaReceber_AVencer.getModel();
                dtmContareceber_AVencer.setNumRows(0);
                
                i = cr.retornaContaReceber(valor, filtro, 0);
                
                while(i.hasNext()){
                    cr = (Contareceber)i.next();
                    dtmContareceber_AVencer.addRow(cr.getContaReceber());
                }
                
                break;
                
            case 7:
                dtmContareceber_Vencido = null;
                dtmContareceber_Vencido = (DefaultTableModel)tblContaReceber_Vencido.getModel();
                dtmContareceber_Vencido.setNumRows(0);
                
                i = cr.retornaContaReceber(valor, filtro, 1);
                
                while(i.hasNext()){
                    cr = (Contareceber)i.next();
                    dtmContareceber_Vencido.addRow(cr.getContaReceber());
                }
                break;
                
            case 8:
                dtmContareceber_Recebido = null;
                dtmContareceber_Recebido = (DefaultTableModel)tblContaReceber_Recebido.getModel();
                dtmContareceber_Recebido.setNumRows(0);
                
                i = cr.retornaContaReceber(valor, filtro, 2);
                
                while(i.hasNext()){
                    cr = (Contareceber)i.next();
                    dtmContareceber_Recebido.addRow(cr.getContaReceber());
                }
                break;
                
        }

    }
   
    
    /*
        Metodos do Fornecedor
    */
    private void VisualizarPerfilFornecedor(){
        /*
            Instancia o formulario IFrmFinanceiro_Fornecedor_Visualizar e joga para o formulario o fornecedorId
            para que seja realizadas as devidas ccnsultas.
        */
        IFrmFinanceiro_Fornecedor_Visualizar FornecedorVisualizar = null;
        
        int linha = tblFinanceiro_Fornecedor.getSelectedRow();
        
        if(linha >= 0){
            id = Integer.valueOf(dtmFornecedor.getValueAt(linha, 0).toString());
            if(FornecedorVisualizar == null){
                FornecedorVisualizar = new IFrmFinanceiro_Fornecedor_Visualizar(id);
            }
            if(!FornecedorVisualizar.isVisible()){
                FrmPrincipal.dskPrincipal.add(FornecedorVisualizar);
                FornecedorVisualizar.setLocation(
                   dskPrincipal.getWidth()/2 - FornecedorVisualizar.getWidth()/2,
                   dskPrincipal.getHeight()/2 - FornecedorVisualizar.getHeight()/2);
                FornecedorVisualizar.show();
           }
            
            try{
                FornecedorVisualizar.toFront();
                FornecedorVisualizar.setSelected(true);
            }catch(Exception ex){
                ex.printStackTrace();
            }
            
        }else{
            JOptionPane.showMessageDialog(this, "Nenhum fornecedor selecionado. Impossivel completar a operação.","ATENÇÃO",JOptionPane.ERROR_MESSAGE);
        }
    }
    //Visualizar Conta Pagar a Vencer
    private void ContaPagar_VisualizarAVencer(){
        IFrmContaPagar_VisualizarContaPagar contaPagar_VisualizarAVencer = null;
        int linha = tblContaPagar_AVencer.getSelectedRow();
       
        if(linha >= 0){
            id = Integer.valueOf(dtmContapagar_AVencer.getValueAt(linha, 0).toString());
            if(contaPagar_VisualizarAVencer == null){
                contaPagar_VisualizarAVencer = new IFrmContaPagar_VisualizarContaPagar(id);
            }
            
            if(!contaPagar_VisualizarAVencer.isVisible()){
                FrmPrincipal.dskPrincipal.add(contaPagar_VisualizarAVencer);
                contaPagar_VisualizarAVencer.setLocation(
                   dskPrincipal.getWidth()/2 - contaPagar_VisualizarAVencer.getWidth()/2,
                   dskPrincipal.getHeight()/2 - contaPagar_VisualizarAVencer.getHeight()/2);
                contaPagar_VisualizarAVencer.show();
            }
            
            try{
                contaPagar_VisualizarAVencer.toFront();
                contaPagar_VisualizarAVencer.setSelected(true);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }else{
            JOptionPane.showMessageDialog(this, "Nenhuma conta selecionada. Impossivel completar a operação.","ATENÇÃO",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //Visualizar contaPagar a pagar vencida
    private void ContaPagar_VisualizarVencido(){
        IFrmContaPagar_VisualizarContaPagar contaPagar_VisualizarVencido = null;
        int linha = tblContaPagar_Vencido.getSelectedRow();
        
        if(linha >= 0){
            id = Integer.valueOf(dtmContapagar_Vencida.getValueAt(linha, 0).toString());
            if(contaPagar_VisualizarVencido == null){
                contaPagar_VisualizarVencido = new IFrmContaPagar_VisualizarContaPagar(id);
            }
            
            if(!contaPagar_VisualizarVencido.isVisible()){
                FrmPrincipal.dskPrincipal.add(contaPagar_VisualizarVencido);
                contaPagar_VisualizarVencido.setLocation(
                   dskPrincipal.getWidth()/2 - contaPagar_VisualizarVencido.getWidth()/2,
                   dskPrincipal.getHeight()/2 - contaPagar_VisualizarVencido.getHeight()/2);
                contaPagar_VisualizarVencido.show();
            }
            
            try{
                contaPagar_VisualizarVencido.toFront();
                contaPagar_VisualizarVencido.setSelected(true);
            }catch(Exception ex){
                ex.printStackTrace();
            } 
           
        }else{
            JOptionPane.showMessageDialog(this, "Nenhuma conta selecionada. Impossivel completar a operação.","ATENÇÃO",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void ContaPagar_VisualizarPago(){
        IFrmContaPagar_VisualizarContaPagar contaPagar_VisualizarPago = null;
        
        int linha = tblContaPagar_Pago.getSelectedRow();
        
        if(linha >= 0){
            id = Integer.valueOf(dtmContapagar_Pago.getValueAt(linha, 0).toString());
            
            if(contaPagar_VisualizarPago == null){
                contaPagar_VisualizarPago = new IFrmContaPagar_VisualizarContaPagar(id);
            }
            
            if(!contaPagar_VisualizarPago.isVisible()){
                FrmPrincipal.dskPrincipal.add(contaPagar_VisualizarPago);
                contaPagar_VisualizarPago.setLocation(
                   dskPrincipal.getWidth()/2 - contaPagar_VisualizarPago.getWidth()/2,
                   dskPrincipal.getHeight()/2 - contaPagar_VisualizarPago.getHeight()/2);
                contaPagar_VisualizarPago.show();
            }
            
            try{
                contaPagar_VisualizarPago.toFront();
                contaPagar_VisualizarPago.setSelected(true);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    
    private void ContaReceber_VisualizarAVencer(){
        IFrmContaReceber_VisualizarContaReceber contaReceber_VisualizarAVencer = null;
        int linha = tblContaReceber_AVencer.getSelectedRow();
       
        if(linha >= 0){
            id = Integer.valueOf(dtmContareceber_AVencer.getValueAt(linha, 0).toString());
            if(contaReceber_VisualizarAVencer == null){
                contaReceber_VisualizarAVencer = new IFrmContaReceber_VisualizarContaReceber(id);
            }
            
            if(!contaReceber_VisualizarAVencer.isVisible()){
                FrmPrincipal.dskPrincipal.add(contaReceber_VisualizarAVencer);
                contaReceber_VisualizarAVencer.setLocation(
                   dskPrincipal.getWidth()/2 - contaReceber_VisualizarAVencer.getWidth()/2,
                   dskPrincipal.getHeight()/2 - contaReceber_VisualizarAVencer.getHeight()/2);
                contaReceber_VisualizarAVencer.show();
            }
            
            try{
                contaReceber_VisualizarAVencer.toFront();
                contaReceber_VisualizarAVencer.setSelected(true);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }else{
            JOptionPane.showMessageDialog(this, "Nenhuma conta selecionada. Impossivel completar a operação.","ATENÇÃO",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    
    //Conta a Pagar   
    public static void atualizaAgendarPagamento_Fornecedor(){
         if(fornecedor.getFornecedorId() != null){
            fornecedor = fornecedor.retornaFornecedor(fornecedor.getFornecedorId());
            txtAgendarPagamento_NomeFantasia.setText(fornecedor.getPessoa().getNome());
            txtAgendarPagamento_CodigoFornecedor.setValue(fornecedor.getFornecedorId());
            txtAgendarPagamento_RazaoSocial.setText(fornecedor.getRazao());
            txtAgendarPagamento_Cnpj.setText(fornecedor.getCnpj());
        }
    }
    
    public void cadastraTransacao(){
            this.transacao = null;
            this.transacao = new Transacao(situacao.retornaSituacao(6), txtAgendarPagamento_Descricao.getText());
            this.transacao.cadastraTransacao(transacao);
    }
    
    /*
        Limpar Campos
    */
    
    public void limparCamposAgendarPagamento(){
        txtAgendarPagamento_NomeFantasia.setText("");
        txtAgendarPagamento_CodigoFornecedor.setText("");
        txtAgendarPagamento_RazaoSocial.setText("");
        txtAgendarPagamento_Cnpj.setText("");
        frmtAgendarPagamento_NDocumento.setValue(0);
        dtaAgendarPagamento_Emissao.setDate(null);
        dtaAgendarPagamento_Vencimento.setDate(null);
        spnAgendarPagamento_Valor.setValue((float)0);
        spnAgendarPagamento_Desconto.setValue((float)0);
        spnAgendarPagamento_Multa.setValue((float)0);
        txtAgendarPagamento_Descricao.setText("");
    }
    
    /*
        Atualiza tabelas, metodos estaticos
    */
    public static void atualizaTblFornecedor(){
        if(txtFornecedor_Pesquisa.getText().equals("")){
            carregaTabela(null, 0, 1);
        }else{
            carregaTabela(txtFornecedor_Pesquisa.getText().toUpperCase(), cmbFornecedor_Filtro.getSelectedIndex(), 1);
        }
        lblFornecedor_Total_Fornecedor.setText("Total: "+dtmFornecedor.getRowCount());
    }
    
    public static void atualizaTblContaPagar_AVencer(){
        if(txtContaPagar_Pesquisa_AVencer.getText().equals("")){
            carregaTabela(null, 0, 2);
        }else{
            carregaTabela(txtContaPagar_Pesquisa_AVencer.getText().toUpperCase(), cmbContaPagar_Filtro_AVencer.getSelectedIndex(), 2);
        }
        lblContaPagar_Total_AVencer.setText("Total: "+dtmContapagar_AVencer.getRowCount());
    }
    
    public static void atualizaTblContaPagar_Vencida(){
        if(txtContaPagar_Pesquisa_Vencida.getText().equals("")){
            carregaTabela(null, 0, 3);
        }else{
            carregaTabela(txtContaPagar_Pesquisa_Vencida.getText().toUpperCase(), cmbContaPagar_Filtro_Vencidas.getSelectedIndex(), 3);
        }
        lblContaPagar_Total_Vencido.setText("Total: "+dtmContapagar_Vencida.getRowCount());
    }
    
    public static void atualizaTblContaPagas_Pago(){
        if(txtContaPagar_Pesquisa_Pago.getText().equals("")){
            carregaTabela(null, 0, 4);
        }else{
            carregaTabela(txtContaPagar_Pesquisa_Pago.getText().toUpperCase(), cmbContaPagar_Filtro_Pago.getSelectedIndex(), 4);
        }
        lblContaPagar_Total_Pago.setText("Total: "+dtmContapagar_Pago.getRowCount());
        
    }
    
    public static void atualizaTblContaReceber_AVencer(){
        if(txtContaReceber_Pesquisa_AVencer.getText().equals("")){
            carregaTabela(null, 0, 6);
        }else{
            carregaTabela(txtContaReceber_Pesquisa_AVencer.getText().toUpperCase(), cmbContaReceber_Filtro_AVencer.getSelectedIndex(), 6);
        }
        lblContaReceber_Total_AVencer.setText("Total: "+dtmContareceber_AVencer.getRowCount());
    }
    
    public static void atualizaTblContaReceber_Vencido(){
        if(txtContaReceber_Pesquisa_Vencido.getText().equals("")){
            carregaTabela(null, 0, 7);
        }else{
            carregaTabela(txtContaReceber_Pesquisa_Vencido.getText().toUpperCase(), cmbContaReceber_Filtro_Vencido.getSelectedIndex(), 7);
        }
        lblContaReceber_Total_Vencido.setText("Total: "+dtmContareceber_Vencido.getRowCount());
    }
    
    public static void atualizaTblContaReceber_Recebido(){
        if(txtContaReceber_Pesquisa_Recebido.getText().equals("")){
            carregaTabela(null, 0, 8);
        }else{
            carregaTabela(txtContaReceber_Pesquisa_Recebido.getText().toUpperCase(), cmbContaReceber_Filtro_Recebido.getSelectedIndex(), 8);
        }
        lblContaReceber_Total_Recebido.setText("Total: "+dtmContareceber_Recebido.getRowCount());
    }
    
    
    public static void atualizaAllTbl(){
        atualizaTblContaPagar_AVencer();
        atualizaTblContaPagar_Vencida();
        atualizaTblContaPagas_Pago();
        atualizaTblFornecedor();
        atualizaTblContaReceber_AVencer();
        atualizaTblContaReceber_Recebido();
        atualizaTblContaReceber_Vencido();
    }
    
    public static void atualizaCaixa(){
        spnCaixa_SaldoInicial.setValue(saldoInicial);
        spnCaixa_TotalEntrada.setValue(totalEntrada);
        spnCaixa_TotalSaida.setValue(totalSaida);
        atualizaValorAtual();
        spnCaixa_SaldoAtual.setValue(saldoAtual);
    }
    
    public static void zerarCaixa(){
        totalEntrada = 0;
        totalSaida = 0;
        saldoInicial = 0;
        saldoFinal = 0;
        saldoAtual = 0;
        atualizaCaixa();
    }
    
    public static void atualizaValorAtual(){
        saldoAtual = (saldoInicial+totalEntrada)-totalSaida;
    }
    
    public IFrmFinanceiro(Usuario usuario) {
        initComponents();
        atualizaAllTbl();
        this.usuario = usuario;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        btnAgendarPagamento_Gravar = new javax.swing.JButton();
        btnAgendarPagamento_Limpar = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        lblNomeFantasia = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtAgendarPagamento_RazaoSocial = new javax.swing.JTextField();
        txtAgendarPagamento_NomeFantasia = new javax.swing.JTextField();
        btnAgendarPagamento_AdicionarFornecedor = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtAgendarPagamento_Cnpj = new javax.swing.JTextField();
        txtAgendarPagamento_CodigoFornecedor = new javax.swing.JFormattedTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        scrlDescricao = new javax.swing.JScrollPane();
        txtAgendarPagamento_Descricao = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        dtaAgendarPagamento_Emissao = new com.toedter.calendar.JDateChooser();
        dtaAgendarPagamento_Vencimento = new com.toedter.calendar.JDateChooser();
        jLabel23 = new javax.swing.JLabel();
        spnAgendarPagamento_Parcela = new javax.swing.JSpinner();
        spnAgendarPagamento_Valor = new javax.swing.JSpinner();
        spnAgendarPagamento_Desconto = new javax.swing.JSpinner();
        spnAgendarPagamento_Multa = new javax.swing.JSpinner();
        frmtAgendarPagamento_NDocumento = new javax.swing.JFormattedTextField();
        cmbAgendarPagamento_TipoConta = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCaixa_Transacao = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        spnCaixa_SaldoInicial = new javax.swing.JFormattedTextField();
        spnCaixa_TotalEntrada = new javax.swing.JFormattedTextField();
        spnCaixa_TotalSaida = new javax.swing.JFormattedTextField();
        spnCaixa_SaldoAtual = new javax.swing.JFormattedTextField();
        jLabel20 = new javax.swing.JLabel();
        txtCaixa_Pesquisa = new javax.swing.JTextField();
        cmbCaixa_Filtro = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        btnCaixa_Deposito = new javax.swing.JButton();
        btnCaixa_Saque = new javax.swing.JButton();
        btnCaixa_AbrirCaixa = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        Recebido2 = new javax.swing.JTabbedPane();
        pnlContaPagar_AVencer = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tblContaPagar_AVencer = new javax.swing.JTable();
        txtContaPagar_Pesquisa_AVencer = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        cmbContaPagar_Filtro_AVencer = new javax.swing.JComboBox<>();
        btnContaPagar_Visualizar_AVencer = new javax.swing.JButton();
        btnContaPagar_Relatorio_AVencer = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        lblContaPagar_Total_AVencer = new javax.swing.JLabel();
        pnlContaPagar_Vencido = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        tblContaPagar_Vencido = new javax.swing.JTable();
        txtContaPagar_Pesquisa_Vencida = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        cmbContaPagar_Filtro_Vencidas = new javax.swing.JComboBox<>();
        btnContaPagar_Visualizar_Vencido = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        btnContaPagar_Relatorio_Vencido = new javax.swing.JButton();
        lblContaPagar_Total_Vencido = new javax.swing.JLabel();
        pnlContaPagar_Pago = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        tblContaPagar_Pago = new javax.swing.JTable();
        txtContaPagar_Pesquisa_Pago = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        cmbContaPagar_Filtro_Pago = new javax.swing.JComboBox<>();
        btnContaPagar_Visualizar_Pago = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        btnContaPagar_Relatorio_Pago = new javax.swing.JButton();
        lblContaPagar_Total_Pago = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        Recebido = new javax.swing.JTabbedPane();
        pnlContaReceber = new javax.swing.JPanel();
        btnContaReceber_Visualiza_AVencer = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        cmbContaReceber_Filtro_AVencer = new javax.swing.JComboBox<>();
        jScrollPane17 = new javax.swing.JScrollPane();
        tblContaReceber_AVencer = new javax.swing.JTable();
        jLabel29 = new javax.swing.JLabel();
        txtContaReceber_Pesquisa_AVencer = new javax.swing.JTextField();
        btnContaReceber_Relatorio_AVencer = new javax.swing.JButton();
        lblContaReceber_Total_AVencer = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        btnContaReceber_Relatorio_Vencido = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        cmbContaReceber_Filtro_Vencido = new javax.swing.JComboBox<>();
        jScrollPane18 = new javax.swing.JScrollPane();
        tblContaReceber_Vencido = new javax.swing.JTable();
        jLabel28 = new javax.swing.JLabel();
        txtContaReceber_Pesquisa_Vencido = new javax.swing.JTextField();
        btnContaReceber_Visualizar_Vencido = new javax.swing.JButton();
        lblContaReceber_Total_Vencido = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        cmbContaReceber_Filtro_Recebido = new javax.swing.JComboBox<>();
        jScrollPane19 = new javax.swing.JScrollPane();
        tblContaReceber_Recebido = new javax.swing.JTable();
        jLabel27 = new javax.swing.JLabel();
        txtContaReceber_Pesquisa_Recebido = new javax.swing.JTextField();
        btnContaReceber_Visualizar_Recebido = new javax.swing.JButton();
        btnContaReceber_Relatorio_Recebido = new javax.swing.JButton();
        lblContaReceber_Total_Recebido = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        txtFornecedor_Pesquisa = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblFinanceiro_Fornecedor = new javax.swing.JTable();
        jLabel39 = new javax.swing.JLabel();
        cmbFornecedor_Filtro = new javax.swing.JComboBox<>();
        btnFornecedor_Visualizar_Fornecedor = new javax.swing.JButton();
        btnFornecedor_Cadastrar_Fornecedor = new javax.swing.JButton();
        btnFornecedor_Relatorio = new javax.swing.JButton();
        lblFornecedor_Total_Fornecedor = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Financeiro");
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

        jTabbedPane1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jTabbedPane1ComponentShown(evt);
            }
        });

        btnAgendarPagamento_Gravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/013-calendar.png"))); // NOI18N
        btnAgendarPagamento_Gravar.setText("Agendar");
        btnAgendarPagamento_Gravar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAgendarPagamento_Gravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgendarPagamento_GravarActionPerformed(evt);
            }
        });

        btnAgendarPagamento_Limpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/two-circling-arrows.png"))); // NOI18N
        btnAgendarPagamento_Limpar.setText("Limpar");
        btnAgendarPagamento_Limpar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAgendarPagamento_Limpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgendarPagamento_LimparActionPerformed(evt);
            }
        });

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Informações"));

        lblNomeFantasia.setText("Nome Fantasia ");

        jLabel4.setText("Razão Social");

        txtAgendarPagamento_RazaoSocial.setEditable(false);

        txtAgendarPagamento_NomeFantasia.setEditable(false);

        btnAgendarPagamento_AdicionarFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/014-search-1.png"))); // NOI18N
        btnAgendarPagamento_AdicionarFornecedor.setText("Selecionar");
        btnAgendarPagamento_AdicionarFornecedor.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnAgendarPagamento_AdicionarFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgendarPagamento_AdicionarFornecedorActionPerformed(evt);
            }
        });

        jLabel3.setText("Código");

        jLabel2.setText("CNPJ");

        txtAgendarPagamento_Cnpj.setEditable(false);

        txtAgendarPagamento_CodigoFornecedor.setEditable(false);
        txtAgendarPagamento_CodigoFornecedor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNomeFantasia)
                    .addComponent(jLabel4))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(txtAgendarPagamento_RazaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtAgendarPagamento_Cnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtAgendarPagamento_NomeFantasia)
                        .addGap(18, 18, 18)
                        .addComponent(btnAgendarPagamento_AdicionarFornecedor)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtAgendarPagamento_CodigoFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNomeFantasia)
                    .addComponent(txtAgendarPagamento_NomeFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgendarPagamento_AdicionarFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addComponent(txtAgendarPagamento_CodigoFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtAgendarPagamento_RazaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtAgendarPagamento_Cnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Agendar conta"));

        jLabel5.setText("Valor (R$)");

        jLabel6.setText("Desconto (R$)");

        jLabel7.setText("Multa (R$)");

        txtAgendarPagamento_Descricao.setColumns(20);
        txtAgendarPagamento_Descricao.setLineWrap(true);
        txtAgendarPagamento_Descricao.setRows(5);
        scrlDescricao.setViewportView(txtAgendarPagamento_Descricao);

        jLabel9.setText("Emissão");

        jLabel10.setText("Vencimento");

        jLabel8.setText("Descrição");

        jLabel22.setText(" Nº Documento");

        jLabel23.setText("N. Parcelas");

        spnAgendarPagamento_Valor.setModel(new javax.swing.SpinnerNumberModel(0.0f, null, null, 0.01f));

        spnAgendarPagamento_Desconto.setModel(new javax.swing.SpinnerNumberModel(0.0f, null, null, 0.01f));

        spnAgendarPagamento_Multa.setModel(new javax.swing.SpinnerNumberModel(0.0f, null, null, 0.01f));

        frmtAgendarPagamento_NDocumento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        cmbAgendarPagamento_TipoConta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Conta a Pagar", "Conta a Receber" }));
        cmbAgendarPagamento_TipoConta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAgendarPagamento_TipoContaActionPerformed(evt);
            }
        });

        jLabel30.setText(" Tipo de Conta");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(18, 18, 18)
                                .addComponent(frmtAgendarPagamento_NDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(spnAgendarPagamento_Valor, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(jLabel6)
                                .addGap(13, 13, 13)
                                .addComponent(spnAgendarPagamento_Desconto, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(spnAgendarPagamento_Multa, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(scrlDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10))
                                .addGap(15, 15, 15)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dtaAgendarPagamento_Emissao, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                                    .addComponent(dtaAgendarPagamento_Vencimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addComponent(jLabel23)
                                        .addGap(33, 33, 33)
                                        .addComponent(spnAgendarPagamento_Parcela, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addComponent(jLabel30)
                                        .addGap(18, 18, 18)
                                        .addComponent(cmbAgendarPagamento_TipoConta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(23, 23, 23))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(15, 15, 15))))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(frmtAgendarPagamento_NDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(spnAgendarPagamento_Valor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(spnAgendarPagamento_Desconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(spnAgendarPagamento_Multa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addGap(11, 11, 11)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(dtaAgendarPagamento_Vencimento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel23)
                                .addComponent(spnAgendarPagamento_Parcela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dtaAgendarPagamento_Emissao, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel30)
                                .addComponent(cmbAgendarPagamento_TipoConta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(scrlDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAgendarPagamento_Gravar, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(btnAgendarPagamento_Limpar, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgendarPagamento_Gravar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgendarPagamento_Limpar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Agendar pagamento", jPanel1);

        jPanel2.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel2ComponentShown(evt);
            }
        });

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Transações"));

        tblCaixa_Transacao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N. TRANSAÇÃO", "DATA", "DESCRIÇÃO", "TIPO", "VALOR"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCaixa_Transacao.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblCaixa_Transacao);
        if (tblCaixa_Transacao.getColumnModel().getColumnCount() > 0) {
            tblCaixa_Transacao.getColumnModel().getColumn(0).setMinWidth(5);
            tblCaixa_Transacao.getColumnModel().getColumn(0).setPreferredWidth(5);
            tblCaixa_Transacao.getColumnModel().getColumn(1).setMinWidth(5);
            tblCaixa_Transacao.getColumnModel().getColumn(1).setPreferredWidth(5);
            tblCaixa_Transacao.getColumnModel().getColumn(2).setMinWidth(40);
            tblCaixa_Transacao.getColumnModel().getColumn(2).setPreferredWidth(40);
            tblCaixa_Transacao.getColumnModel().getColumn(3).setMinWidth(5);
            tblCaixa_Transacao.getColumnModel().getColumn(3).setPreferredWidth(5);
            tblCaixa_Transacao.getColumnModel().getColumn(4).setMinWidth(10);
            tblCaixa_Transacao.getColumnModel().getColumn(4).setPreferredWidth(10);
        }

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Saldo"));

        jLabel12.setText("R$");

        jLabel13.setText("R$");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("Total entrada");

        jLabel15.setText("R$");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setText("Total saida");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setText("Saldo Atual");

        jLabel18.setText("R$");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setText("Saldo Inicial");

        spnCaixa_SaldoInicial.setEditable(false);
        spnCaixa_SaldoInicial.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("¤#,##0.00"))));

        spnCaixa_TotalEntrada.setEditable(false);
        spnCaixa_TotalEntrada.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("¤#,##0.00"))));

        spnCaixa_TotalSaida.setEditable(false);
        spnCaixa_TotalSaida.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("¤#,##0.00"))));

        spnCaixa_SaldoAtual.setEditable(false);
        spnCaixa_SaldoAtual.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("¤#,##0.00"))));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel19)
                        .addGap(43, 43, 43))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(15, 15, 15)
                                .addComponent(spnCaixa_SaldoAtual))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel16))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(15, 15, 15)
                                .addComponent(spnCaixa_TotalSaida)))))
                .addGap(10, 10, 10))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(44, 57, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(15, 15, 15)
                                .addComponent(spnCaixa_TotalEntrada))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(15, 15, 15)
                                .addComponent(spnCaixa_SaldoInicial)))
                        .addGap(10, 10, 10))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addGap(10, 10, 10)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(spnCaixa_SaldoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(jLabel14)
                .addGap(10, 10, 10)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(spnCaixa_TotalEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(jLabel16)
                .addGap(10, 10, 10)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(spnCaixa_TotalSaida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(jLabel17)
                .addGap(10, 10, 10)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(spnCaixa_SaldoAtual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/014-search-1.png"))); // NOI18N

        cmbCaixa_Filtro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N.Transação", "Descricao", "Saida", "Entrada", "Valor" }));

        jLabel21.setText("Pesquisar por");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(6, 6, 6)
                        .addComponent(txtCaixa_Pesquisa)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel21)
                        .addGap(18, 18, 18)
                        .addComponent(cmbCaixa_Filtro, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(txtCaixa_Pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbCaixa_Filtro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        btnCaixa_Deposito.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/png/002-coin.png"))); // NOI18N
        btnCaixa_Deposito.setText("Deposito");
        btnCaixa_Deposito.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCaixa_Deposito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCaixa_DepositoActionPerformed(evt);
            }
        });

        btnCaixa_Saque.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/png/003-wallet.png"))); // NOI18N
        btnCaixa_Saque.setText("Saque");
        btnCaixa_Saque.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCaixa_Saque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCaixa_SaqueActionPerformed(evt);
            }
        });

        btnCaixa_AbrirCaixa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/016-point-of-service.png"))); // NOI18N
        btnCaixa_AbrirCaixa.setText("Abrir Caixa");
        btnCaixa_AbrirCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCaixa_AbrirCaixaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnCaixa_Deposito)
                        .addGap(55, 55, 55)
                        .addComponent(btnCaixa_Saque, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCaixa_AbrirCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCaixa_Deposito, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCaixa_Saque, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCaixa_AbrirCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        jTabbedPane1.addTab("Caixa", jPanel2);

        pnlContaPagar_AVencer.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                pnlContaPagar_AVencerComponentShown(evt);
            }
        });

        tblContaPagar_AVencer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "BENEFICIARIO", "VENCIMENTO", "CNPJ", "VALOR"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblContaPagar_AVencer.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                tblContaPagar_AVencerComponentShown(evt);
            }
        });
        jScrollPane13.setViewportView(tblContaPagar_AVencer);
        if (tblContaPagar_AVencer.getColumnModel().getColumnCount() > 0) {
            tblContaPagar_AVencer.getColumnModel().getColumn(0).setResizable(false);
            tblContaPagar_AVencer.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblContaPagar_AVencer.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblContaPagar_AVencer.getColumnModel().getColumn(2).setPreferredWidth(20);
            tblContaPagar_AVencer.getColumnModel().getColumn(3).setPreferredWidth(30);
            tblContaPagar_AVencer.getColumnModel().getColumn(4).setPreferredWidth(20);
        }

        txtContaPagar_Pesquisa_AVencer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtContaPagar_Pesquisa_AVencerKeyReleased(evt);
            }
        });

        jLabel41.setText("Pesquisar por");

        cmbContaPagar_Filtro_AVencer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Beneficiario", "CNPJ", "Valor" }));

        btnContaPagar_Visualizar_AVencer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/png/007-invoice-1.png"))); // NOI18N
        btnContaPagar_Visualizar_AVencer.setText("Visualizar Conta");
        btnContaPagar_Visualizar_AVencer.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnContaPagar_Visualizar_AVencer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContaPagar_Visualizar_AVencerActionPerformed(evt);
            }
        });

        btnContaPagar_Relatorio_AVencer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/png/003-business.png"))); // NOI18N
        btnContaPagar_Relatorio_AVencer.setText("Relatorio");
        btnContaPagar_Relatorio_AVencer.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnContaPagar_Relatorio_AVencer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContaPagar_Relatorio_AVencerActionPerformed(evt);
            }
        });

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/014-search-1.png"))); // NOI18N

        lblContaPagar_Total_AVencer.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblContaPagar_Total_AVencer.setText("Total");

        javax.swing.GroupLayout pnlContaPagar_AVencerLayout = new javax.swing.GroupLayout(pnlContaPagar_AVencer);
        pnlContaPagar_AVencer.setLayout(pnlContaPagar_AVencerLayout);
        pnlContaPagar_AVencerLayout.setHorizontalGroup(
            pnlContaPagar_AVencerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContaPagar_AVencerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 782, Short.MAX_VALUE)
                .addGap(10, 10, 10))
            .addGroup(pnlContaPagar_AVencerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtContaPagar_Pesquisa_AVencer)
                .addGap(18, 18, 18)
                .addComponent(jLabel41)
                .addGap(20, 20, 20)
                .addComponent(cmbContaPagar_Filtro_AVencer, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(pnlContaPagar_AVencerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnContaPagar_Visualizar_AVencer)
                .addGap(50, 50, 50)
                .addComponent(btnContaPagar_Relatorio_AVencer, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblContaPagar_Total_AVencer, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlContaPagar_AVencerLayout.setVerticalGroup(
            pnlContaPagar_AVencerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContaPagar_AVencerLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlContaPagar_AVencerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtContaPagar_Pesquisa_AVencer, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlContaPagar_AVencerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbContaPagar_Filtro_AVencer, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel41)
                        .addComponent(jLabel24)))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                .addGap(5, 5, 5)
                .addGroup(pnlContaPagar_AVencerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnContaPagar_Visualizar_AVencer, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnContaPagar_Relatorio_AVencer, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblContaPagar_Total_AVencer))
                .addGap(5, 5, 5))
        );

        Recebido2.addTab("A Vencer", pnlContaPagar_AVencer);

        pnlContaPagar_Vencido.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                pnlContaPagar_VencidoComponentShown(evt);
            }
        });

        tblContaPagar_Vencido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "BENEFICIARIO", "VENCIMENTO", "CNPJ", "VALOR"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane16.setViewportView(tblContaPagar_Vencido);
        if (tblContaPagar_Vencido.getColumnModel().getColumnCount() > 0) {
            tblContaPagar_Vencido.getColumnModel().getColumn(0).setResizable(false);
            tblContaPagar_Vencido.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblContaPagar_Vencido.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblContaPagar_Vencido.getColumnModel().getColumn(2).setPreferredWidth(20);
            tblContaPagar_Vencido.getColumnModel().getColumn(3).setPreferredWidth(30);
            tblContaPagar_Vencido.getColumnModel().getColumn(4).setPreferredWidth(20);
        }

        txtContaPagar_Pesquisa_Vencida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtContaPagar_Pesquisa_VencidaKeyReleased(evt);
            }
        });

        jLabel49.setText("Pesquisar por");

        cmbContaPagar_Filtro_Vencidas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Beneficiario", "CNPJ", "Valor" }));

        btnContaPagar_Visualizar_Vencido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/png/007-invoice-1.png"))); // NOI18N
        btnContaPagar_Visualizar_Vencido.setText("Visualizar Conta");
        btnContaPagar_Visualizar_Vencido.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnContaPagar_Visualizar_Vencido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContaPagar_Visualizar_VencidoActionPerformed(evt);
            }
        });

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/014-search-1.png"))); // NOI18N

        btnContaPagar_Relatorio_Vencido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/png/003-business.png"))); // NOI18N
        btnContaPagar_Relatorio_Vencido.setText("Relatorio");
        btnContaPagar_Relatorio_Vencido.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnContaPagar_Relatorio_Vencido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContaPagar_Relatorio_VencidoActionPerformed(evt);
            }
        });

        lblContaPagar_Total_Vencido.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblContaPagar_Total_Vencido.setText("Total");

        javax.swing.GroupLayout pnlContaPagar_VencidoLayout = new javax.swing.GroupLayout(pnlContaPagar_Vencido);
        pnlContaPagar_Vencido.setLayout(pnlContaPagar_VencidoLayout);
        pnlContaPagar_VencidoLayout.setHorizontalGroup(
            pnlContaPagar_VencidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContaPagar_VencidoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlContaPagar_VencidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 782, Short.MAX_VALUE)
                    .addGroup(pnlContaPagar_VencidoLayout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtContaPagar_Pesquisa_Vencida)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel49)
                        .addGap(20, 20, 20)
                        .addComponent(cmbContaPagar_Filtro_Vencidas, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlContaPagar_VencidoLayout.createSequentialGroup()
                        .addComponent(btnContaPagar_Visualizar_Vencido)
                        .addGap(50, 50, 50)
                        .addComponent(btnContaPagar_Relatorio_Vencido, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblContaPagar_Total_Vencido, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlContaPagar_VencidoLayout.setVerticalGroup(
            pnlContaPagar_VencidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContaPagar_VencidoLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlContaPagar_VencidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContaPagar_VencidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtContaPagar_Pesquisa_Vencida))
                    .addGroup(pnlContaPagar_VencidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbContaPagar_Filtro_Vencidas, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel49)))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                .addGap(5, 5, 5)
                .addGroup(pnlContaPagar_VencidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnContaPagar_Visualizar_Vencido, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnContaPagar_Relatorio_Vencido, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblContaPagar_Total_Vencido))
                .addGap(5, 5, 5))
        );

        Recebido2.addTab("Vencido", pnlContaPagar_Vencido);

        pnlContaPagar_Pago.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                pnlContaPagar_PagoComponentShown(evt);
            }
        });

        tblContaPagar_Pago.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "BENEFICIARIO", "VENCIMENTO", "CNPJ", "VALOR", "PAGAMENTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane15.setViewportView(tblContaPagar_Pago);
        if (tblContaPagar_Pago.getColumnModel().getColumnCount() > 0) {
            tblContaPagar_Pago.getColumnModel().getColumn(0).setResizable(false);
            tblContaPagar_Pago.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblContaPagar_Pago.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblContaPagar_Pago.getColumnModel().getColumn(2).setPreferredWidth(20);
            tblContaPagar_Pago.getColumnModel().getColumn(3).setPreferredWidth(30);
            tblContaPagar_Pago.getColumnModel().getColumn(4).setPreferredWidth(20);
        }

        txtContaPagar_Pesquisa_Pago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtContaPagar_Pesquisa_PagoKeyReleased(evt);
            }
        });

        jLabel51.setText("Pesquisar por");

        cmbContaPagar_Filtro_Pago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Beneficiario", "CNPJ", "Valor" }));

        btnContaPagar_Visualizar_Pago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/png/007-invoice-1.png"))); // NOI18N
        btnContaPagar_Visualizar_Pago.setText("Visualizar Conta");
        btnContaPagar_Visualizar_Pago.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnContaPagar_Visualizar_Pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContaPagar_Visualizar_PagoActionPerformed(evt);
            }
        });

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/014-search-1.png"))); // NOI18N

        btnContaPagar_Relatorio_Pago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/png/003-business.png"))); // NOI18N
        btnContaPagar_Relatorio_Pago.setText("Relatorio");
        btnContaPagar_Relatorio_Pago.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnContaPagar_Relatorio_Pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContaPagar_Relatorio_PagoActionPerformed(evt);
            }
        });

        lblContaPagar_Total_Pago.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblContaPagar_Total_Pago.setText("Total");

        javax.swing.GroupLayout pnlContaPagar_PagoLayout = new javax.swing.GroupLayout(pnlContaPagar_Pago);
        pnlContaPagar_Pago.setLayout(pnlContaPagar_PagoLayout);
        pnlContaPagar_PagoLayout.setHorizontalGroup(
            pnlContaPagar_PagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContaPagar_PagoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlContaPagar_PagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlContaPagar_PagoLayout.createSequentialGroup()
                        .addGroup(pnlContaPagar_PagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlContaPagar_PagoLayout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtContaPagar_Pesquisa_Pago, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel51)
                                .addGap(20, 20, 20)
                                .addComponent(cmbContaPagar_Filtro_Pago, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlContaPagar_PagoLayout.createSequentialGroup()
                                .addComponent(btnContaPagar_Visualizar_Pago)
                                .addGap(50, 50, 50)
                                .addComponent(btnContaPagar_Relatorio_Pago, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 49, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContaPagar_PagoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblContaPagar_Total_Pago, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlContaPagar_PagoLayout.setVerticalGroup(
            pnlContaPagar_PagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContaPagar_PagoLayout.createSequentialGroup()
                .addGroup(pnlContaPagar_PagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContaPagar_PagoLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(pnlContaPagar_PagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtContaPagar_Pesquisa_Pago, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlContaPagar_PagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmbContaPagar_Filtro_Pago, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel51))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContaPagar_PagoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel26)))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                .addGap(5, 5, 5)
                .addGroup(pnlContaPagar_PagoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnContaPagar_Visualizar_Pago, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnContaPagar_Relatorio_Pago, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblContaPagar_Total_Pago))
                .addGap(5, 5, 5))
        );

        Recebido2.addTab("Pago", pnlContaPagar_Pago);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Recebido2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(Recebido2)
                .addGap(10, 10, 10))
        );

        jTabbedPane1.addTab("Contas a Pagar", jPanel3);

        pnlContaReceber.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                pnlContaReceberComponentShown(evt);
            }
        });

        btnContaReceber_Visualiza_AVencer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/png/007-invoice-1.png"))); // NOI18N
        btnContaReceber_Visualiza_AVencer.setText("Visualizar Conta");
        btnContaReceber_Visualiza_AVencer.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnContaReceber_Visualiza_AVencer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContaReceber_Visualiza_AVencerActionPerformed(evt);
            }
        });

        jLabel33.setText("Pesquisar por");

        cmbContaReceber_Filtro_AVencer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "DESCRIÇÃO", "VALOR", "MULTA", "TOTAL" }));

        tblContaReceber_AVencer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DESCRIÇÃO", "VALOR", "MULTA", "TOTAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane17.setViewportView(tblContaReceber_AVencer);
        if (tblContaReceber_AVencer.getColumnModel().getColumnCount() > 0) {
            tblContaReceber_AVencer.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblContaReceber_AVencer.getColumnModel().getColumn(2).setPreferredWidth(20);
            tblContaReceber_AVencer.getColumnModel().getColumn(3).setPreferredWidth(30);
            tblContaReceber_AVencer.getColumnModel().getColumn(4).setPreferredWidth(5);
        }

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/014-search-1.png"))); // NOI18N

        txtContaReceber_Pesquisa_AVencer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtContaReceber_Pesquisa_AVencerKeyReleased(evt);
            }
        });

        btnContaReceber_Relatorio_AVencer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/png/003-business.png"))); // NOI18N
        btnContaReceber_Relatorio_AVencer.setText("Relatorio");
        btnContaReceber_Relatorio_AVencer.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnContaReceber_Relatorio_AVencer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContaReceber_Relatorio_AVencerActionPerformed(evt);
            }
        });

        lblContaReceber_Total_AVencer.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblContaReceber_Total_AVencer.setText("Total");

        javax.swing.GroupLayout pnlContaReceberLayout = new javax.swing.GroupLayout(pnlContaReceber);
        pnlContaReceber.setLayout(pnlContaReceberLayout);
        pnlContaReceberLayout.setHorizontalGroup(
            pnlContaReceberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContaReceberLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlContaReceberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 782, Short.MAX_VALUE)
                    .addGroup(pnlContaReceberLayout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtContaReceber_Pesquisa_AVencer)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbContaReceber_Filtro_AVencer, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlContaReceberLayout.createSequentialGroup()
                        .addComponent(btnContaReceber_Visualiza_AVencer)
                        .addGap(50, 50, 50)
                        .addComponent(btnContaReceber_Relatorio_AVencer, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblContaReceber_Total_AVencer, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );
        pnlContaReceberLayout.setVerticalGroup(
            pnlContaReceberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContaReceberLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlContaReceberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContaReceberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtContaReceber_Pesquisa_AVencer, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addGroup(pnlContaReceberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbContaReceber_Filtro_AVencer, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel33)))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlContaReceberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnContaReceber_Visualiza_AVencer, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnContaReceber_Relatorio_AVencer, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblContaReceber_Total_AVencer))
                .addGap(5, 5, 5))
        );

        Recebido.addTab("A Vencer", pnlContaReceber);

        jPanel19.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel19ComponentShown(evt);
            }
        });

        btnContaReceber_Relatorio_Vencido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/png/003-business.png"))); // NOI18N
        btnContaReceber_Relatorio_Vencido.setText("Relatorio");
        btnContaReceber_Relatorio_Vencido.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnContaReceber_Relatorio_Vencido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContaReceber_Relatorio_VencidoActionPerformed(evt);
            }
        });

        jLabel35.setText("Pesquisar por");

        cmbContaReceber_Filtro_Vencido.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "DESCRIÇÃO", "VALOR", "MULTA", "TOTAL" }));

        tblContaReceber_Vencido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DESCRIÇÃO", "VALOR", "MULTA", "TOTAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane18.setViewportView(tblContaReceber_Vencido);
        if (tblContaReceber_Vencido.getColumnModel().getColumnCount() > 0) {
            tblContaReceber_Vencido.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblContaReceber_Vencido.getColumnModel().getColumn(2).setPreferredWidth(20);
            tblContaReceber_Vencido.getColumnModel().getColumn(3).setPreferredWidth(30);
            tblContaReceber_Vencido.getColumnModel().getColumn(4).setPreferredWidth(20);
        }

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/014-search-1.png"))); // NOI18N

        txtContaReceber_Pesquisa_Vencido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtContaReceber_Pesquisa_VencidoKeyReleased(evt);
            }
        });

        btnContaReceber_Visualizar_Vencido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/png/007-invoice-1.png"))); // NOI18N
        btnContaReceber_Visualizar_Vencido.setText("Visualizar Conta");

        lblContaReceber_Total_Vencido.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblContaReceber_Total_Vencido.setText("Total");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 782, Short.MAX_VALUE)
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtContaReceber_Pesquisa_Vencido)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel35)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cmbContaReceber_Filtro_Vencido, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(btnContaReceber_Visualizar_Vencido)
                        .addGap(50, 50, 50)
                        .addComponent(btnContaReceber_Relatorio_Vencido, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblContaReceber_Total_Vencido, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtContaReceber_Pesquisa_Vencido, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbContaReceber_Filtro_Vencido, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel35)))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane18, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnContaReceber_Visualizar_Vencido, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnContaReceber_Relatorio_Vencido, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblContaReceber_Total_Vencido))
                .addGap(5, 5, 5))
        );

        Recebido.addTab("Vencido", jPanel19);

        jPanel20.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel20ComponentShown(evt);
            }
        });

        jLabel37.setText("Pesquisar por");

        cmbContaReceber_Filtro_Recebido.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "DESCRIÇÃO", "VALOR", "MULTA", "TOTAL" }));

        tblContaReceber_Recebido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DESCRIÇÃO", "VALOR", "MULTA", "TOTAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane19.setViewportView(tblContaReceber_Recebido);
        if (tblContaReceber_Recebido.getColumnModel().getColumnCount() > 0) {
            tblContaReceber_Recebido.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblContaReceber_Recebido.getColumnModel().getColumn(2).setPreferredWidth(20);
            tblContaReceber_Recebido.getColumnModel().getColumn(3).setPreferredWidth(30);
            tblContaReceber_Recebido.getColumnModel().getColumn(4).setPreferredWidth(20);
        }

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/014-search-1.png"))); // NOI18N

        txtContaReceber_Pesquisa_Recebido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtContaReceber_Pesquisa_RecebidoKeyReleased(evt);
            }
        });

        btnContaReceber_Visualizar_Recebido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/png/007-invoice-1.png"))); // NOI18N
        btnContaReceber_Visualizar_Recebido.setText("Visualizar Conta");
        btnContaReceber_Visualizar_Recebido.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        btnContaReceber_Relatorio_Recebido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/png/003-business.png"))); // NOI18N
        btnContaReceber_Relatorio_Recebido.setText("Relatorio");
        btnContaReceber_Relatorio_Recebido.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnContaReceber_Relatorio_Recebido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContaReceber_Relatorio_RecebidoActionPerformed(evt);
            }
        });

        lblContaReceber_Total_Recebido.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblContaReceber_Total_Recebido.setText("Total");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtContaReceber_Pesquisa_Recebido)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbContaReceber_Filtro_Recebido, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 782, Short.MAX_VALUE)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(btnContaReceber_Visualizar_Recebido)
                        .addGap(50, 50, 50)
                        .addComponent(btnContaReceber_Relatorio_Recebido, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblContaReceber_Total_Recebido, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtContaReceber_Pesquisa_Recebido, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbContaReceber_Filtro_Recebido, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel37)))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane19, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnContaReceber_Visualizar_Recebido, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnContaReceber_Relatorio_Recebido, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblContaReceber_Total_Recebido))
                .addGap(5, 5, 5))
        );

        Recebido.addTab("Recebido", jPanel20);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Recebido)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(Recebido)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Contas a Receber", jPanel4);

        jPanel5.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel5ComponentShown(evt);
            }
        });

        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Visualização de fornecedores"));

        txtFornecedor_Pesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFornecedor_PesquisaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFornecedor_PesquisaKeyReleased(evt);
            }
        });

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/014-search-1.png"))); // NOI18N

        tblFinanceiro_Fornecedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "RAZÃO SOCIAL", "NOME FANTASIA", "CNPJ", "TEL. FIXO", "E-MAIL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane11.setViewportView(tblFinanceiro_Fornecedor);
        if (tblFinanceiro_Fornecedor.getColumnModel().getColumnCount() > 0) {
            tblFinanceiro_Fornecedor.getColumnModel().getColumn(0).setResizable(false);
            tblFinanceiro_Fornecedor.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblFinanceiro_Fornecedor.getColumnModel().getColumn(1).setPreferredWidth(60);
            tblFinanceiro_Fornecedor.getColumnModel().getColumn(2).setPreferredWidth(40);
            tblFinanceiro_Fornecedor.getColumnModel().getColumn(3).setPreferredWidth(50);
            tblFinanceiro_Fornecedor.getColumnModel().getColumn(4).setPreferredWidth(5);
            tblFinanceiro_Fornecedor.getColumnModel().getColumn(5).setPreferredWidth(20);
        }

        jLabel39.setText("Filtro");

        cmbFornecedor_Filtro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Razão Social", "Nome Fantasia", "CNPJ" }));

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane11))
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFornecedor_Pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                        .addComponent(jLabel39)
                        .addGap(18, 18, 18)
                        .addComponent(cmbFornecedor_Filtro, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel38)
                    .addComponent(txtFornecedor_Pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbFornecedor_Filtro, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel39)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnFornecedor_Visualizar_Fornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/png/001-man.png"))); // NOI18N
        btnFornecedor_Visualizar_Fornecedor.setText("Visualizar Fornecedor");
        btnFornecedor_Visualizar_Fornecedor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnFornecedor_Visualizar_Fornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFornecedor_Visualizar_FornecedorActionPerformed(evt);
            }
        });

        btnFornecedor_Cadastrar_Fornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/png/001-hand-shake.png"))); // NOI18N
        btnFornecedor_Cadastrar_Fornecedor.setText("Cadastrar Fornecedor");
        btnFornecedor_Cadastrar_Fornecedor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnFornecedor_Cadastrar_Fornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFornecedor_Cadastrar_FornecedorActionPerformed(evt);
            }
        });

        btnFornecedor_Relatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/png/003-business.png"))); // NOI18N
        btnFornecedor_Relatorio.setText("Relatorio");
        btnFornecedor_Relatorio.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnFornecedor_Relatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFornecedor_RelatorioActionPerformed(evt);
            }
        });

        lblFornecedor_Total_Fornecedor.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblFornecedor_Total_Fornecedor.setText("Total");
        lblFornecedor_Total_Fornecedor.setFocusable(false);
        lblFornecedor_Total_Fornecedor.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnFornecedor_Cadastrar_Fornecedor)
                        .addGap(62, 62, 62)
                        .addComponent(btnFornecedor_Visualizar_Fornecedor)
                        .addGap(79, 79, 79)
                        .addComponent(btnFornecedor_Relatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblFornecedor_Total_Fornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFornecedor_Cadastrar_Fornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFornecedor_Visualizar_Fornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFornecedor_Relatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFornecedor_Total_Fornecedor))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Fornecedor", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 832, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing

    }//GEN-LAST:event_formInternalFrameClosing

    private void jTabbedPane1ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jTabbedPane1ComponentShown
       atualizaTblFornecedor();
    }//GEN-LAST:event_jTabbedPane1ComponentShown

    private void jPanel5ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel5ComponentShown
        carregaTabela(null, 0, 1);
    }//GEN-LAST:event_jPanel5ComponentShown

    private void btnFornecedor_Cadastrar_FornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFornecedor_Cadastrar_FornecedorActionPerformed
        /*
        Instancia e exibe o formulario IFrmFinanceiro_Fornecedor_Cadastro
        */
        IFrmFinanceiro_Fornecedor_Cadastro FornecedorCadastro = null;

        FornecedorCadastro = new IFrmFinanceiro_Fornecedor_Cadastro(null);

        if(!FornecedorCadastro.isVisible()){
            FrmPrincipal.dskPrincipal.add(FornecedorCadastro);
            FornecedorCadastro.setLocation(
                dskPrincipal.getWidth()/2 - FornecedorCadastro.getWidth()/2,
                dskPrincipal.getHeight()/2 - FornecedorCadastro.getHeight()/2);
            FornecedorCadastro.show();
        }

        try{
            FornecedorCadastro.toFront();
            FornecedorCadastro.setSelected(true);
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }//GEN-LAST:event_btnFornecedor_Cadastrar_FornecedorActionPerformed

    private void btnFornecedor_Visualizar_FornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFornecedor_Visualizar_FornecedorActionPerformed
        VisualizarPerfilFornecedor();
    }//GEN-LAST:event_btnFornecedor_Visualizar_FornecedorActionPerformed

    private void txtFornecedor_PesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFornecedor_PesquisaKeyReleased
        atualizaTblFornecedor();
    }//GEN-LAST:event_txtFornecedor_PesquisaKeyReleased

    private void txtFornecedor_PesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFornecedor_PesquisaKeyPressed
        atualizaTblFornecedor();
    }//GEN-LAST:event_txtFornecedor_PesquisaKeyPressed

    private void pnlContaPagar_PagoComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pnlContaPagar_PagoComponentShown
        atualizaTblContaPagas_Pago();
    }//GEN-LAST:event_pnlContaPagar_PagoComponentShown

    private void btnContaPagar_Visualizar_PagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContaPagar_Visualizar_PagoActionPerformed
        ContaPagar_VisualizarPago();
    }//GEN-LAST:event_btnContaPagar_Visualizar_PagoActionPerformed

    private void txtContaPagar_Pesquisa_PagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContaPagar_Pesquisa_PagoKeyReleased
        atualizaTblContaPagas_Pago();
    }//GEN-LAST:event_txtContaPagar_Pesquisa_PagoKeyReleased

    private void pnlContaPagar_VencidoComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pnlContaPagar_VencidoComponentShown
        atualizaTblContaPagar_Vencida();
    }//GEN-LAST:event_pnlContaPagar_VencidoComponentShown

    private void btnContaPagar_Visualizar_VencidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContaPagar_Visualizar_VencidoActionPerformed
        ContaPagar_VisualizarVencido();
    }//GEN-LAST:event_btnContaPagar_Visualizar_VencidoActionPerformed

    private void txtContaPagar_Pesquisa_VencidaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContaPagar_Pesquisa_VencidaKeyReleased
       atualizaTblContaPagar_Vencida();
    }//GEN-LAST:event_txtContaPagar_Pesquisa_VencidaKeyReleased

    private void pnlContaPagar_AVencerComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pnlContaPagar_AVencerComponentShown
        atualizaTblContaPagar_AVencer();
    }//GEN-LAST:event_pnlContaPagar_AVencerComponentShown

    private void btnContaPagar_Visualizar_AVencerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContaPagar_Visualizar_AVencerActionPerformed
        ContaPagar_VisualizarAVencer();
    }//GEN-LAST:event_btnContaPagar_Visualizar_AVencerActionPerformed

    private void txtContaPagar_Pesquisa_AVencerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContaPagar_Pesquisa_AVencerKeyReleased
        atualizaTblContaPagar_AVencer();
    }//GEN-LAST:event_txtContaPagar_Pesquisa_AVencerKeyReleased

    private void tblContaPagar_AVencerComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_tblContaPagar_AVencerComponentShown
        atualizaTblContaPagar_AVencer();
    }//GEN-LAST:event_tblContaPagar_AVencerComponentShown

    private void btnCaixa_DepositoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCaixa_DepositoActionPerformed
        IFrmCaixa_SaqueDeposito iFrmCaixa_SaqueDeposito = new IFrmCaixa_SaqueDeposito(0);
        
        if(!iFrmCaixa_SaqueDeposito.isVisible()){
             FrmPrincipal.dskPrincipal.add(iFrmCaixa_SaqueDeposito);
            iFrmCaixa_SaqueDeposito.setLocation(
                dskPrincipal.getWidth()/2 - iFrmCaixa_SaqueDeposito.getWidth()/2,
                dskPrincipal.getHeight()/2 - iFrmCaixa_SaqueDeposito.getHeight()/2);
            iFrmCaixa_SaqueDeposito.show();
        }
        
        try{
            iFrmCaixa_SaqueDeposito.toFront();
            iFrmCaixa_SaqueDeposito.setSelected(true);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnCaixa_DepositoActionPerformed

    private void btnAgendarPagamento_AdicionarFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgendarPagamento_AdicionarFornecedorActionPerformed
        
        IFrmAgendarPagamento_SelecionarFornecedor iFrmSelecionarFornecedor = new IFrmAgendarPagamento_SelecionarFornecedor(fornecedor);

        if(!iFrmSelecionarFornecedor.isVisible()){
            FrmPrincipal.dskPrincipal.add(iFrmSelecionarFornecedor);
            iFrmSelecionarFornecedor.setLocation(
                dskPrincipal.getWidth()/2 - iFrmSelecionarFornecedor.getWidth()/2,
                dskPrincipal.getHeight()/2 - iFrmSelecionarFornecedor.getHeight()/2);
            iFrmSelecionarFornecedor.show();
        }

        try{
            iFrmSelecionarFornecedor.toFront();
            iFrmSelecionarFornecedor.setSelected(true);
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }//GEN-LAST:event_btnAgendarPagamento_AdicionarFornecedorActionPerformed

    private void btnAgendarPagamento_LimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgendarPagamento_LimparActionPerformed
        limparCamposAgendarPagamento();
    }//GEN-LAST:event_btnAgendarPagamento_LimparActionPerformed

    private void btnAgendarPagamento_GravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgendarPagamento_GravarActionPerformed
        if(FrmPrincipal.caixa != null){
            if((!txtAgendarPagamento_CodigoFornecedor.getText().equals("") && !txtAgendarPagamento_Cnpj.getText().equals("")) || cmbAgendarPagamento_TipoConta.getSelectedIndex() == 1){
                if(cmbAgendarPagamento_TipoConta.getSelectedIndex() == 0){
                    Contapagar contaPagar = new Contapagar(fornecedor,new Date(dtaAgendarPagamento_Vencimento.getDate().getTime()),new Date(dtaAgendarPagamento_Emissao.getDate().getTime()), (float)spnAgendarPagamento_Valor.getValue(), (float)spnAgendarPagamento_Desconto.getValue(), (int)spnAgendarPagamento_Parcela.getValue(), (float)spnAgendarPagamento_Multa.getValue(), frmtAgendarPagamento_NDocumento.getValue().toString());

                    if(contaPagar.getParcela() <= 1 ){
                        cadastraTransacao();
                        contaPagar.setTransacao(transacao);
                        contaPagar.cadastraContaPagar(contaPagar);
                        JOptionPane.showMessageDialog(this, "Conta de "+contaPagar.getFornecedor().getRazao()+" cadastrada com sucesso!!","SUCESSO",JOptionPane.INFORMATION_MESSAGE);
                    }else if(contaPagar.getParcela() > 1){
                        float valorMensal = contaPagar.getTotal()/contaPagar.getParcela();
                        contaPagar.setTotal(valorMensal);
                        Calendar c = Calendar.getInstance();

                        for(int aux = 1; aux <= contaPagar.getParcela(); aux++){
                            cadastraTransacao();
                            contaPagar.setTransacao(transacao);
                            contaPagar.cadastraContaPagar(contaPagar);
                            c.setTime(contaPagar.getDataVencimento());
                            c.set(c.MONTH,c.get(c.MONTH)+1);
                            contaPagar.setDataVencimento(c.getTime());
                        }
                        JOptionPane.showMessageDialog(this, "Conta de "+contaPagar.getFornecedor().getRazao()+" cadastrada com sucesso!!","SUCESSO",JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(this, "Conta de "+contaPagar.getFornecedor().getRazao()+" não foi cadastrado!!","ERRO",JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    Contareceber contaReceber = new Contareceber(new Date(dtaAgendarPagamento_Vencimento.getDate().getTime()), (float)spnAgendarPagamento_Valor.getValue(), (float)spnAgendarPagamento_Desconto.getValue(), (float)spnAgendarPagamento_Multa.getValue(), new Date(System.currentTimeMillis()), (int)spnAgendarPagamento_Parcela.getValue(), txtAgendarPagamento_Descricao.getText());
                    if(contaReceber.getParcela() <= 1){
                        cadastraTransacao();
                        contaReceber.setTransacao(transacao);
                        contaReceber.cadastraContaReceber(contaReceber);
                        JOptionPane.showMessageDialog(this, "Conta de "+contaReceber.getFornecedor().getRazao()+" cadastrada com sucesso!!","SUCESSO",JOptionPane.INFORMATION_MESSAGE);
                    }else if(contaReceber.getParcela() > 1){
                        float valorMensal = contaReceber.getTotal()/contaReceber.getParcela();
                        contaReceber.setTotal(valorMensal);

                        Calendar c = Calendar.getInstance();
                        for(int aux = 1; aux<= contaReceber.getParcela();aux++){
                            cadastraTransacao();
                            contaReceber.setTransacao(transacao);
                            contaReceber.cadastraContaReceber(contaReceber);
                            c.setTime(contaReceber.getVencimento());
                            c.set(c.MONTH, c.get(c.MONTH)+1);
                            contaReceber.setDataEmissao(c.getTime());
                        }
                        JOptionPane.showMessageDialog(this, "Conta no valor de R$"+contaReceber.getTotal()+" cadastrada com sucesso!!","SUCESSO",JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(this, "Conta no valor de R$"+contaReceber.getTotal()+" não foi cadastrado!!","ERRO",JOptionPane.ERROR_MESSAGE);
                    }
                }
                limparCamposAgendarPagamento();
                carregaTabela(null, 0, 2);
                atualizaAllTbl();
            }else{
                JOptionPane.showMessageDialog(this, "Não foi selecionado um fornecedor para vinculação.","ATENÇÃO",JOptionPane.ERROR_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Caixa não está aberto. Impossivel continuar!","ATENÇÃO",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAgendarPagamento_GravarActionPerformed

    private void btnFornecedor_RelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFornecedor_RelatorioActionPerformed
        fornecedor.populaRelatorio();
    }//GEN-LAST:event_btnFornecedor_RelatorioActionPerformed

    private void btnContaPagar_Relatorio_AVencerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContaPagar_Relatorio_AVencerActionPerformed
        String sql =  "from Contapagar where transacao.situacao.situacaoId = '6'";
        contaPagar.gerarRelatorio(sql);
    }//GEN-LAST:event_btnContaPagar_Relatorio_AVencerActionPerformed

    private void jPanel2ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel2ComponentShown
        if(FrmPrincipal.caixa != null){
            carregaTabela(null, -1, 5);
        }
    }//GEN-LAST:event_jPanel2ComponentShown

    private void txtContaReceber_Pesquisa_RecebidoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContaReceber_Pesquisa_RecebidoKeyReleased
        atualizaTblContaReceber_Recebido();
    }//GEN-LAST:event_txtContaReceber_Pesquisa_RecebidoKeyReleased

    private void txtContaReceber_Pesquisa_VencidoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContaReceber_Pesquisa_VencidoKeyReleased
        atualizaTblContaReceber_Vencido();
    }//GEN-LAST:event_txtContaReceber_Pesquisa_VencidoKeyReleased

    private void txtContaReceber_Pesquisa_AVencerKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContaReceber_Pesquisa_AVencerKeyReleased
        atualizaTblContaReceber_AVencer();
    }//GEN-LAST:event_txtContaReceber_Pesquisa_AVencerKeyReleased

    private void btnCaixa_AbrirCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCaixa_AbrirCaixaActionPerformed
        IFrmCaixa_AbrirFechar iFrmCaixa_AbrirCaixa = null;
        
        if(iFrmCaixa_AbrirCaixa == null){
            iFrmCaixa_AbrirCaixa = new IFrmCaixa_AbrirFechar();
        }
        
        if(!iFrmCaixa_AbrirCaixa.isVisible()){
            FrmPrincipal.dskPrincipal.add(iFrmCaixa_AbrirCaixa);
            iFrmCaixa_AbrirCaixa.setLocation(
                dskPrincipal.getWidth()/2 - iFrmCaixa_AbrirCaixa.getWidth()/2,
                dskPrincipal.getHeight()/2 - iFrmCaixa_AbrirCaixa.getHeight()/2);
            iFrmCaixa_AbrirCaixa.show();
        }
        
        try{
            iFrmCaixa_AbrirCaixa.toFront();
            iFrmCaixa_AbrirCaixa.setSelected(true);
           
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnCaixa_AbrirCaixaActionPerformed

    private void btnCaixa_SaqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCaixa_SaqueActionPerformed
        IFrmCaixa_SaqueDeposito iFrmCaixa_SaqueDeposito = new IFrmCaixa_SaqueDeposito(1);
        
        if(!iFrmCaixa_SaqueDeposito.isVisible()){
             FrmPrincipal.dskPrincipal.add(iFrmCaixa_SaqueDeposito);
            iFrmCaixa_SaqueDeposito.setLocation(
                dskPrincipal.getWidth()/2 - iFrmCaixa_SaqueDeposito.getWidth()/2,
                dskPrincipal.getHeight()/2 - iFrmCaixa_SaqueDeposito.getHeight()/2);
            iFrmCaixa_SaqueDeposito.show();
        }
        
        try{
            iFrmCaixa_SaqueDeposito.toFront();
            iFrmCaixa_SaqueDeposito.setSelected(true);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnCaixa_SaqueActionPerformed

    private void btnContaPagar_Relatorio_VencidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContaPagar_Relatorio_VencidoActionPerformed
        String sql =  "from Contapagar where transacao.situacao.situacaoId = '1'";
        contaPagar.gerarRelatorio(sql);
    }//GEN-LAST:event_btnContaPagar_Relatorio_VencidoActionPerformed

    private void btnContaPagar_Relatorio_PagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContaPagar_Relatorio_PagoActionPerformed
        String sql =  "from Contapagar where transacao.situacao.situacaoId = '2'";
        contaPagar.gerarRelatorio(sql);
    }//GEN-LAST:event_btnContaPagar_Relatorio_PagoActionPerformed

    private void pnlContaReceberComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pnlContaReceberComponentShown
        atualizaTblContaReceber_AVencer();
    }//GEN-LAST:event_pnlContaReceberComponentShown

    private void btnContaReceber_Visualiza_AVencerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContaReceber_Visualiza_AVencerActionPerformed
        ContaReceber_VisualizarAVencer();
    }//GEN-LAST:event_btnContaReceber_Visualiza_AVencerActionPerformed

    private void jPanel19ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel19ComponentShown
        atualizaTblContaReceber_Vencido();
    }//GEN-LAST:event_jPanel19ComponentShown

    private void jPanel20ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel20ComponentShown
        atualizaTblContaReceber_Recebido();
    }//GEN-LAST:event_jPanel20ComponentShown

    private void cmbAgendarPagamento_TipoContaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAgendarPagamento_TipoContaActionPerformed
        if(cmbAgendarPagamento_TipoConta.getSelectedIndex() == 1){
            txtAgendarPagamento_Cnpj.setEnabled(false);
            txtAgendarPagamento_NomeFantasia.setEnabled(false);
            txtAgendarPagamento_RazaoSocial.setEnabled(false);
            txtAgendarPagamento_CodigoFornecedor.setEnabled(false);
            btnAgendarPagamento_AdicionarFornecedor.setEnabled(false);
            frmtAgendarPagamento_NDocumento.setEnabled(false);
            dtaAgendarPagamento_Emissao.setEnabled(false);
        }else{
            txtAgendarPagamento_Cnpj.setEnabled(true);
            txtAgendarPagamento_NomeFantasia.setEnabled(true);
            txtAgendarPagamento_RazaoSocial.setEnabled(true);
            txtAgendarPagamento_CodigoFornecedor.setEnabled(true);
            btnAgendarPagamento_AdicionarFornecedor.setEnabled(true);
            frmtAgendarPagamento_NDocumento.setEnabled(true);
            dtaAgendarPagamento_Emissao.setEnabled(true);
        }
    }//GEN-LAST:event_cmbAgendarPagamento_TipoContaActionPerformed

    private void btnContaReceber_Relatorio_AVencerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContaReceber_Relatorio_AVencerActionPerformed
        String sql =  "from Contareceber where transacao.situacao.situacaoId = '6'";
        contaReceber.gerarRelatorio(sql);
    }//GEN-LAST:event_btnContaReceber_Relatorio_AVencerActionPerformed

    private void btnContaReceber_Relatorio_VencidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContaReceber_Relatorio_VencidoActionPerformed
        String sql =  "from Contareceber where transacao.situacao.situacaoId = '1'";
        contaReceber.gerarRelatorio(sql);
    }//GEN-LAST:event_btnContaReceber_Relatorio_VencidoActionPerformed

    private void btnContaReceber_Relatorio_RecebidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContaReceber_Relatorio_RecebidoActionPerformed
        String sql =  "from Contareceber where transacao.situacao.situacaoId = '2'";
        contaReceber.gerarRelatorio(sql);
    }//GEN-LAST:event_btnContaReceber_Relatorio_RecebidoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane Recebido;
    private javax.swing.JTabbedPane Recebido2;
    private javax.swing.JButton btnAgendarPagamento_AdicionarFornecedor;
    private javax.swing.JButton btnAgendarPagamento_Gravar;
    private javax.swing.JButton btnAgendarPagamento_Limpar;
    public static javax.swing.JButton btnCaixa_AbrirCaixa;
    private javax.swing.JButton btnCaixa_Deposito;
    private javax.swing.JButton btnCaixa_Saque;
    private javax.swing.JButton btnContaPagar_Relatorio_AVencer;
    private javax.swing.JButton btnContaPagar_Relatorio_Pago;
    private javax.swing.JButton btnContaPagar_Relatorio_Vencido;
    private javax.swing.JButton btnContaPagar_Visualizar_AVencer;
    private javax.swing.JButton btnContaPagar_Visualizar_Pago;
    private javax.swing.JButton btnContaPagar_Visualizar_Vencido;
    private javax.swing.JButton btnContaReceber_Relatorio_AVencer;
    private javax.swing.JButton btnContaReceber_Relatorio_Recebido;
    private javax.swing.JButton btnContaReceber_Relatorio_Vencido;
    private javax.swing.JButton btnContaReceber_Visualiza_AVencer;
    private javax.swing.JButton btnContaReceber_Visualizar_Recebido;
    private javax.swing.JButton btnContaReceber_Visualizar_Vencido;
    private javax.swing.JButton btnFornecedor_Cadastrar_Fornecedor;
    private javax.swing.JButton btnFornecedor_Relatorio;
    private javax.swing.JButton btnFornecedor_Visualizar_Fornecedor;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmbAgendarPagamento_TipoConta;
    private static javax.swing.JComboBox<String> cmbCaixa_Filtro;
    private static javax.swing.JComboBox<String> cmbContaPagar_Filtro_AVencer;
    private static javax.swing.JComboBox<String> cmbContaPagar_Filtro_Pago;
    private static javax.swing.JComboBox<String> cmbContaPagar_Filtro_Vencidas;
    public static javax.swing.JComboBox<String> cmbContaReceber_Filtro_AVencer;
    public static javax.swing.JComboBox<String> cmbContaReceber_Filtro_Recebido;
    public static javax.swing.JComboBox<String> cmbContaReceber_Filtro_Vencido;
    private static javax.swing.JComboBox<String> cmbFornecedor_Filtro;
    private com.toedter.calendar.JDateChooser dtaAgendarPagamento_Emissao;
    private com.toedter.calendar.JDateChooser dtaAgendarPagamento_Vencimento;
    private javax.swing.JFormattedTextField frmtAgendarPagamento_NDocumento;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private static javax.swing.JLabel lblContaPagar_Total_AVencer;
    private static javax.swing.JLabel lblContaPagar_Total_Pago;
    private static javax.swing.JLabel lblContaPagar_Total_Vencido;
    private static javax.swing.JLabel lblContaReceber_Total_AVencer;
    private static javax.swing.JLabel lblContaReceber_Total_Recebido;
    private static javax.swing.JLabel lblContaReceber_Total_Vencido;
    private static javax.swing.JLabel lblFornecedor_Total_Fornecedor;
    private javax.swing.JLabel lblNomeFantasia;
    private javax.swing.JPanel pnlContaPagar_AVencer;
    private javax.swing.JPanel pnlContaPagar_Pago;
    private javax.swing.JPanel pnlContaPagar_Vencido;
    private javax.swing.JPanel pnlContaReceber;
    private javax.swing.JScrollPane scrlDescricao;
    private javax.swing.JSpinner spnAgendarPagamento_Desconto;
    private javax.swing.JSpinner spnAgendarPagamento_Multa;
    private javax.swing.JSpinner spnAgendarPagamento_Parcela;
    private javax.swing.JSpinner spnAgendarPagamento_Valor;
    public static javax.swing.JFormattedTextField spnCaixa_SaldoAtual;
    public static javax.swing.JFormattedTextField spnCaixa_SaldoInicial;
    public static javax.swing.JFormattedTextField spnCaixa_TotalEntrada;
    public static javax.swing.JFormattedTextField spnCaixa_TotalSaida;
    private static javax.swing.JTable tblCaixa_Transacao;
    private static javax.swing.JTable tblContaPagar_AVencer;
    private static javax.swing.JTable tblContaPagar_Pago;
    private static javax.swing.JTable tblContaPagar_Vencido;
    private static javax.swing.JTable tblContaReceber_AVencer;
    private static javax.swing.JTable tblContaReceber_Recebido;
    private static javax.swing.JTable tblContaReceber_Vencido;
    private static javax.swing.JTable tblFinanceiro_Fornecedor;
    private static javax.swing.JTextField txtAgendarPagamento_Cnpj;
    private static javax.swing.JFormattedTextField txtAgendarPagamento_CodigoFornecedor;
    private javax.swing.JTextArea txtAgendarPagamento_Descricao;
    private static javax.swing.JTextField txtAgendarPagamento_NomeFantasia;
    private static javax.swing.JTextField txtAgendarPagamento_RazaoSocial;
    private static javax.swing.JTextField txtCaixa_Pesquisa;
    private static javax.swing.JTextField txtContaPagar_Pesquisa_AVencer;
    private static javax.swing.JTextField txtContaPagar_Pesquisa_Pago;
    private static javax.swing.JTextField txtContaPagar_Pesquisa_Vencida;
    private static javax.swing.JTextField txtContaReceber_Pesquisa_AVencer;
    private static javax.swing.JTextField txtContaReceber_Pesquisa_Recebido;
    private static javax.swing.JTextField txtContaReceber_Pesquisa_Vencido;
    private static javax.swing.JTextField txtFornecedor_Pesquisa;
    // End of variables declaration//GEN-END:variables
    private Calendar calendario = Calendar.getInstance();

}
