package main.view.sanphamchitiet;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import main.repository.BanHangSPRepositories;
import main.repository.HoaDonChiTietRepository;
import main.repository.SanPhamRepository;
import main.response.BanHangResponse;
import main.response.SanPhamResponse;
import main.view.chucnang.BanHang;

public class ImeiChiTiet extends javax.swing.JFrame {

    private DefaultTableModel dtm;
    private SanPhamRepository sanphamRepository;
    private SanPhamResponse sanphamResponse;
    private BanHangResponse banhangResponse;
    private BanHang BHV;
    private BanHangSPRepositories banhangRepository;
    private HoaDonChiTietRepository hdctRepository;
    ArrayList<String> selectedImei = new ArrayList<>();

    public ImeiChiTiet() {
        initComponents();
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Quản lý Imei");
        dtm = (DefaultTableModel) tblImeiGioHang.getModel();
        sanphamRepository = new SanPhamRepository();
        sanphamResponse = new SanPhamResponse();
        banhangRepository = new BanHangSPRepositories();
        hdctRepository = new HoaDonChiTietRepository();
        this.ShowDataTable(sanphamResponse.getMaSanPham());
    }

    public ImeiChiTiet(String maSP, BanHang banhangView) {
        initComponents();
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Quản lý Imei");
        dtm = (DefaultTableModel) tblImeiGioHang.getModel();
        sanphamRepository = new SanPhamRepository();
        sanphamResponse = new SanPhamResponse();
        banhangResponse = new BanHangResponse();
        banhangRepository = new BanHangSPRepositories();
        hdctRepository = new HoaDonChiTietRepository();
        this.ShowDataTable(maSP);
        BHV = banhangView;
    }

    private void ShowDataTable(String MaSP) {
        dtm.setRowCount(0);
        AtomicInteger index = new AtomicInteger(1);
        banhangRepository.getImeiByMaSP(MaSP).forEach(x -> dtm.addRow(new Object[]{
            index.getAndIncrement(), x.getMaSanPham(), x.getImei(), false
        }));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblImeiGioHang = new javax.swing.JTable();
        btnSelect = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblImeiGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã SP", "Imei", "Chọn"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblImeiGioHang);

        btnSelect.setText("Chọn");
        btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel1.setText("CHỌN IMEI ĐỂ THÊM VÀO GIỎ HÀNG");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSelect)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(60, 60, 60))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSelect)
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectActionPerformed
        for (int i = 0; i < tblImeiGioHang.getRowCount(); i++) {
            Boolean isChecked = (Boolean) tblImeiGioHang.getValueAt(i, 3);
            if (isChecked != null && isChecked) {
                String imei = (String) tblImeiGioHang.getValueAt(i, 2);
                selectedImei.add(imei);
            }
        }
        if (selectedImei.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn Imei muốn bán !");
        } else {
                BHV.addGioHang(banhangRepository.getGiaBanByMa(tblImeiGioHang.getValueAt(0, 1).toString()),
                BHV.getIdByMa(BHV.layMaSPSelect()), selectedImei.size());
                BHV.showDataTableSP(banhangRepository.getAll(BHV.getFormSearch()));
                BHV.showDataGioHang();               
            dispose();
        }
    }//GEN-LAST:event_btnSelectActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ImeiChiTiet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ImeiChiTiet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ImeiChiTiet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ImeiChiTiet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ImeiChiTiet().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSelect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblImeiGioHang;
    // End of variables declaration//GEN-END:variables
}
