<?php
//kết nối database
require "dbConnect.php";
$query="SELECT monan.mamonan,tenmonan,nguyenlieu,congthuc,hinhanh,maloaimonan,monanyeuthich.manguoidung FROM monanyeuthich,monan where monanyeuthich.mamonan=monan.mamonan";
$data=mysqli_query($connection,$query);

class MonAnYeuThich{
	function MonAnYeuThich($mamonan,$tenmonan,$nguyenlieu,$congthuc,$hinhanh,$maloaimonan,$manguoidung)
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
$mangMonAnYeuThich = array();
//3. Thêm phần tử vào mảng
while($row=mysqli_fetch_assoc($data))
{
	array_push($mangMonAnYeuThich,new MonAnYeuThich($row['mamonan'],$row['tenmonan'],$row['nguyenlieu'],$row['congthuc'],$row['hinhanh'],$row['maloaimonan'],$row['manguoidung']));
}

//4. Chuyển định dạng mảng thành JSON
echo json_encode($mangMonAnYeuThich);

?>