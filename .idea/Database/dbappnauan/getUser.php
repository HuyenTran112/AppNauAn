<?php
//kết nối database
require "dbConnect.php";
$query="SELECT * FROM nguoidung";
$data=mysqli_query($connection,$query);

class NguoiDung{
	function NguoiDung($manguoidung,$email,$tenhienthi, $matkhau, $maloainguoidung, $hinhanh)
	{
		$this->manguoidung=$manguoidung;
		$this->email=$email;
		$this->tenhienthi=$tenhienthi;
		$this->matkhau=$matkhau;
		$this->maloainguoidung=$maloainguoidung;
		$this->hinhanh=$hinhanh;
	}
}
//Tạo mảng 
$mangNguoiDung = array();
//3. Thêm phần tử vào mảng
while($row=mysqli_fetch_assoc($data))
{
	array_push($mangNguoiDung,new NguoiDung($row['manguoidung'],$row['email'],$row['tenhienthi'],$row['matkhau'], $row['maloainguoidung'],$row['hinhanh']));
}

//4. Chuyển định dạng mảng thành JSON
echo json_encode($mangNguoiDung);

?>