package main.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TaiKhoan {

    private Integer id;
    private Integer idNhanVien;
    private String userName;
    private String Pass;
    private Boolean vaiTro;
    private Boolean TrangThai;
}
