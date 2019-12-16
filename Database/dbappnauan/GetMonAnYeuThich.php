<?php
//kết nối database
require "dbConnect.php";
$query="SELECT * FROM monanyeuthich";
$data=mysqli_query($connection,$query);

class MonAnYeuThich{
	function MonAnYeuThich($mamonan,$manguoidung)
	{
		$this->mamonan=$mamonan;
		$this->manguoidung=$manguoidung;
	}
}
//2. Tạo mảng 
$mangMonAnYeuThich = array();
//3. Thêm phần tử vào mảng
while($row=mysqli_fetch_assoc($data))
{
	array_push($mangMonAnYeuThich,new MonAnYeuThich($row['mamonan'],$row['manguoidung']));
}

//4. Chuyển định dạng mảng thành JSON
echo json_encode($mangMonAnYeuThich);

?>