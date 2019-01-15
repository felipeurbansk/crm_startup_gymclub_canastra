/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import controller.Resposta;
import controller.Chamado;
import java.text.SimpleDateFormat;
/**
 *
 * @author Urbanski
 */
public class IFrmSuporte_VisualizarChamado_VisualizarResposta extends javax.swing.JInternalFrame {

    /**
     * Creates new form IFrmSuporte_VisualizarChamado_VisualizarResposta
     */
    Resposta resposta = null;
    private SimpleDateFormat dataExibicao = new SimpleDateFormat("ddMMYYYY HHssmm");
    
    
    public IFrmSuporte_VisualizarChamado_VisualizarResposta(int respostaId) {
        initComponents();
        resposta = new Resposta().retornaResposta(respostaId);
        popularCampos();
    }
    
    public void popularCampos(){
        txtSuporte_VisualizarChamado_VisualizarResposta_Remetente.setText(resposta.getChamado().getUsuario().getPessoa().getNome());
        txtSuporte_VisualizarChamado_VisualizarResposta_Destinatario.setText(resposta.getChamado().getUsuario().getPessoa().getNome());
        frmtSuporte_VisualizarChamado_VisualizarResposta_DataCriacao.setText(dataExibicao.format(resposta.getChamado().getDataCriacao()));
        frmtSuporte_VisualizarChamado_VisualizarResposta_DataResposta.setText(dataExibicao.format(resposta.getData()));
        txtSuporte_VisualizarChamado_VisualizarResposta_Assunto.setText(resposta.getChamado().getAssunto());
        txtSuporte_VisualizarChamado_VisualizarResposta_Resposta.setText(resposta.getResposta());
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
        txtSuporte_VisualizarChamado_VisualizarResposta_Assunto = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtSuporte_VisualizarChamado_VisualizarResposta_Remetente = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtSuporte_VisualizarChamado_VisualizarResposta_Resposta = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        txtSuporte_VisualizarChamado_VisualizarResposta_Destinatario = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        frmtSuporte_VisualizarChamado_VisualizarResposta_DataCriacao = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        frmtSuporte_VisualizarChamado_VisualizarResposta_DataResposta = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Visualizar Resposta");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Resposta"));

        txtSuporte_VisualizarChamado_VisualizarResposta_Assunto.setEditable(false);

        jLabel6.setText("Resposta");

        jLabel1.setText("Remetente");

        txtSuporte_VisualizarChamado_VisualizarResposta_Remetente.setEditable(false);

        txtSuporte_VisualizarChamado_VisualizarResposta_Resposta.setEditable(false);
        txtSuporte_VisualizarChamado_VisualizarResposta_Resposta.setColumns(20);
        txtSuporte_VisualizarChamado_VisualizarResposta_Resposta.setRows(5);
        jScrollPane1.setViewportView(txtSuporte_VisualizarChamado_VisualizarResposta_Resposta);

        jLabel2.setText("Destinatario");

        txtSuporte_VisualizarChamado_VisualizarResposta_Destinatario.setEditable(false);

        jButton1.setText("Fechar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Chamado criado");

        frmtSuporte_VisualizarChamado_VisualizarResposta_DataCriacao.setEditable(false);
        try {
            frmtSuporte_VisualizarChamado_VisualizarResposta_DataCriacao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/#### ##:##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel4.setText("Resposta");

        frmtSuporte_VisualizarChamado_VisualizarResposta_DataResposta.setEditable(false);
        try {
            frmtSuporte_VisualizarChamado_VisualizarResposta_DataResposta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/#### ##:##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel5.setText("Assunto");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(23, 23, 23)
                            .addComponent(txtSuporte_VisualizarChamado_VisualizarResposta_Remetente, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(33, 33, 33)
                            .addComponent(jLabel2)
                            .addGap(18, 18, 18)
                            .addComponent(txtSuporte_VisualizarChamado_VisualizarResposta_Destinatario, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(18, 18, 18)
                            .addComponent(txtSuporte_VisualizarChamado_VisualizarResposta_Assunto))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(18, 18, 18)
                            .addComponent(frmtSuporte_VisualizarChamado_VisualizarResposta_DataCriacao, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(51, 51, 51)
                            .addComponent(jLabel4)
                            .addGap(18, 18, 18)
                            .addComponent(frmtSuporte_VisualizarChamado_VisualizarResposta_DataResposta, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtSuporte_VisualizarChamado_VisualizarResposta_Remetente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtSuporte_VisualizarChamado_VisualizarResposta_Destinatario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(frmtSuporte_VisualizarChamado_VisualizarResposta_DataCriacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(frmtSuporte_VisualizarChamado_VisualizarResposta_DataResposta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtSuporte_VisualizarChamado_VisualizarResposta_Assunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField frmtSuporte_VisualizarChamado_VisualizarResposta_DataCriacao;
    private javax.swing.JFormattedTextField frmtSuporte_VisualizarChamado_VisualizarResposta_DataResposta;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtSuporte_VisualizarChamado_VisualizarResposta_Assunto;
    private javax.swing.JTextField txtSuporte_VisualizarChamado_VisualizarResposta_Destinatario;
    private javax.swing.JTextField txtSuporte_VisualizarChamado_VisualizarResposta_Remetente;
    private javax.swing.JTextArea txtSuporte_VisualizarChamado_VisualizarResposta_Resposta;
    // End of variables declaration//GEN-END:variables
}
