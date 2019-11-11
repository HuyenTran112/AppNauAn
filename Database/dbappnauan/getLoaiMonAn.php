<?php
//kết nối database
require "dbConnect.php";
$query="SELECT * FROM loaimonan";
$data=mysqli_query($connection,$query);

class LoaiMonAn{
	function LoaiMonAn($maloaimonan,$tenloaimonan)
	{
		$this->maloaimonan=$maloaimonan;
		$this->tenloaimonan=$tenloaimonan;
	}
}
//2. Tạo mảng 
$mangLoaiMonAn = array();
//3. Thêm phần tử vào mảng
while($row=mysqli_fetch_assoc($data))
{
	array_push($mangLoaiMonAn,new LoaiMonAn($row['maloaimonan'],$row['tenloaimonan']));
}

//4. Chuyển định dạng mảng thành JSON
echo json_encode($mangLoaiMonAn);

?>