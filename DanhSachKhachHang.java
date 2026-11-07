import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class DanhSachKhachHang {
    private ArrayList<Nguoi> danhSachNguoi;

    public DanhSachKhachHang() {
        this.danhSachNguoi = new ArrayList<>();
    }

    public ArrayList<Nguoi> getDanhSachNguoi() {
        return danhSachNguoi;
    }

    public boolean them(Nguoi person) {
        if (person == null || person.getNumberPhone() == null || person.getNumberPhone().trim().isEmpty()) {
            System.out.println("Loi: Thong tin nguoi dung khong hop le!");
            return false;
        }
        if (timKiemTheoSDT(person.getNumberPhone()) != null) {
            System.out.println("Loi: So dien thoai " + person.getNumberPhone() + " da ton tai trong he thong!");
            return false;
        }
        danhSachNguoi.add(person);
        return true;
    }

    public Nguoi timKiemTheoSDT(String phone) {
        if (phone == null) return null;
        for (Nguoi p : danhSachNguoi) {
            if (p.getNumberPhone() != null && p.getNumberPhone().equals(phone.trim())) {
                return p;
            }
        }
        return null;
    }

    public boolean xoaTheoSDT(String phone) {
        if (phone == null) return false;
        for (int i = 0; i < danhSachNguoi.size(); i++) {
            if (danhSachNguoi.get(i).getNumberPhone() != null && danhSachNguoi.get(i).getNumberPhone().equals(phone.trim())) {
                danhSachNguoi.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean sua(String phone, String newName) {
        Nguoi p = timKiemTheoSDT(phone);
        if (p != null && newName != null && !newName.trim().isEmpty()) {
            p.setName(newName.trim());
            return true;
        }
        return false;
    }

    public void hienThiDanhSach() {
        if (danhSachNguoi.isEmpty()) {
            System.out.println("Chua co thong tin khach hang/nhan vien nao.");
            return;
        }
        System.out.println("\n+--------------------+---------------+---------------------+--------------------+");
        System.out.println("| Ho va Ten          | So Dien Thoai | Loai Doi Tuong      | Ma The / Ma NV     |");
        System.out.println("+--------------------+---------------+---------------------+--------------------+");
        for (Nguoi p : danhSachNguoi) {
            String extra = "-";
            if (p instanceof KhachHangVIP) {
                extra = ((KhachHangVIP) p).getCardCodeVIP();
            } else if (p instanceof KhachHangThanThiet) {
                extra = "Diem: " + ((KhachHangThanThiet) p).getDiemTichLuy();
            } else if (p instanceof NhanVienQuanLy) {
                extra = ((NhanVienQuanLy) p).getStaffID() + " (QL)";
            } else if (p instanceof NhanVien) {
                extra = ((NhanVien) p).getStaffID();
            }
            System.out.printf("| %-18s | %-13s | %-19s | %-18s |\n",
                    SanPhamThoiTrang.formatCell(p.getName(), 18),
                    p.getNumberPhone(),
                    p.getClass().getSimpleName(),
                    extra);
        }
        System.out.println("+--------------------+---------------+---------------------+--------------------+\n");
    }

    public void docTuFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                try {
                    String[] parts = line.split(",");
                    if (parts.length < 3) continue;
                    String loai = parts[0].trim();
                    String ten = parts[1].trim();
                    String sdt = parts[2].trim();
                    if (loai.equalsIgnoreCase("VIP") && parts.length >= 4) {
                        them(new KhachHangVIP(ten, sdt, parts[3].trim()));
                    } else if (loai.equalsIgnoreCase("NhanVien") && parts.length >= 4) {
                        them(new NhanVien(ten, sdt, parts[3].trim()));
                    } else if (loai.equalsIgnoreCase("QuanLy") && parts.length >= 5) {
                        double pc = Double.parseDouble(parts[4].trim());
                        them(new NhanVienQuanLy(ten, sdt, parts[3].trim(), pc));
                    } else if (loai.equalsIgnoreCase("ThanThiet") && parts.length >= 4) {
                        int diem = Integer.parseInt(parts[3].trim());
                        them(new KhachHangThanThiet(ten, sdt, diem));
                    }
                } catch (Exception ignored) {}
            }
        } catch (Exception ignored) {}
    }

    public void ghiVaoFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
            for (Nguoi p : danhSachNguoi) {
                if (p instanceof NhanVienQuanLy) {
                    NhanVienQuanLy ql = (NhanVienQuanLy) p;
                    writer.write("QuanLy," + ql.getName() + "," + ql.getNumberPhone() + "," + ql.getStaffID() + "," + ql.getPhuCapTrachNhiem());
                } else if (p instanceof NhanVien) {
                    NhanVien nv = (NhanVien) p;
                    writer.write("NhanVien," + nv.getName() + "," + nv.getNumberPhone() + "," + nv.getStaffID());
                } else if (p instanceof KhachHangVIP) {
                    KhachHangVIP vip = (KhachHangVIP) p;
                    writer.write("VIP," + vip.getName() + "," + vip.getNumberPhone() + "," + vip.getCardCodeVIP());
                } else if (p instanceof KhachHangThanThiet) {
                    KhachHangThanThiet tt = (KhachHangThanThiet) p;
                    writer.write("ThanThiet," + tt.getName() + "," + tt.getNumberPhone() + "," + tt.getDiemTichLuy());
                }
                writer.newLine();
            }
            writer.flush();
        } catch (Exception ignored) {}
    }
}
