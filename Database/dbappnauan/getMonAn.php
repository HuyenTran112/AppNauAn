<?php
//kết nối database
require "dbConnect.php";
$query="SELECT * FROM monan";
$data=mysqli_query($connection,$query);

class MonAn{
	function MonAn($mamonan,$tenmonan,$nguyenlieu, $congthuc, $hinhanh, $maloaimonan,$manguoidung)
	{
		$this->mamonan=$mamonan;
		$this->tenmonan=$tenmonan;
		$this->nguyenlieu=$nguyenlieu;
		$this->congthuc=$congthuc;
		$this->hinhanh=$hinhanh;
		$this->maloaimonan=$maloaimonan;
		$this->manguoidung=$manguoidung;
	}
}
//2. Tạo mảng 
$mangMonAn = array();
//3. Thêm phần tử vào mảng
while($row=mysqli_fetch_assoc($data))
{
	array_push($mangMonAn,new MonAn($row['mamonan'],$row['tenmonan'],$row['nguyenlieu'],$row['congthuc'],$row['hinhanh'],$row['maloaimonan'],$row['manguoidung']));
}

//4. Chuyển định dạng mảng thành JSON
echo json_encode($mangMonAn);

?>