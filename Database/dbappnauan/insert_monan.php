<?php
require 'dbConnect.php';
global $connection;
$upload_path = 'imagemonan/'; //this is our upload folder
$server_ip = "172.17.28.47:8080/"; //Getting the server ip
$upload_url = 'http://'.$server_ip.'AppNauAn/Database/dbappnauan/'.$upload_path;
//response array
$response = array();
 
 
if($_SERVER['REQUEST_METHOD']=='POST'){
 
    //checking the required parameters from the request
    if(isset($_POST['tenmonan']) and isset($_POST['nguyenlieu']) and isset($_POST['congthuc']) and isset($_POST['maloaimonan']) and isset($_POST['manguoidung']))
    {
         
        $tenmonan = $_POST['tenmonan'];
        $nguyenlieu=$_POST['nguyenlieu'];
        $congthuc=$_POST['congthuc'];
        $maloaimonan=$_POST['maloaimonan'];
        $manguoidung=$_POST['manguoidung'];
        $fileinfo = pathinfo($_FILES['image']['name']);//getting file info from the request
        $extension = $fileinfo['extension']; //getting the file extension
        $file_url = $upload_url . getFileName() . '.' . $extension; //file url to store in the database
        $file_path = $upload_path . getFileName() . '.'. $extension; //file path to upload in the server
        $img_name = getFileName() . '.'. $extension; //file name to store in the database
 
        try{
            move_uploaded_file($_FILES['image']['tmp_name'],$file_path); //saving the file to the uploads folder;
           
            //adding the path and name to database
            // $sql = "INSERT INTO monan(tenmonan,nguyenlieu , congthuc,hinhanh,maloaimonan,manguoidung) ";
            // $sql .= "VALUES ('{$tenmonan}', '{$nguyenlieu}', '{$congthuc}','{$file_url},'{$maloaimonan}','{$manguoidung}');";
             $sql = "INSERT INTO monan(mamonan, tenmonan, nguyenlieu,congthuc,hinhanh,maloaimonan,manguoidung) VALUES (null, '$tenmonan', '$nguyenlieu','$congthuc','$file_url','$maloaimonan','$manguoidung')";
    
            if(mysqli_query($connection,$sql)){
                //filling response array with values
                $response['error'] = false;
                $response['tenmonan'] = $tenmonan;
                $response['nguyenlieu'] = $nguyenlieu;
                $response['congthuc'] = $congthuc;
                $response['hinhanh']=$file_url;
                $response['maloaimonan']=$maloaimonan;
                $response['manguoidung']=$manguoidung;
            }
            //if some error occurred
        }catch(Exception $e){
            $response['error']=true;
            $response['message']=$e->getMessage();
        }
        //displaying the response
        echo json_encode($response);
 
        //closing the connection
        mysqli_close($connection);
    }else{
        $response['error'] = true;
        $response['message']='Please choose a file';
    }
}
 
/*
We are generating the file name
so this method will return a file name for the image to be uploaded
*/
function getFileName(){
    global $connection;
     
    $sql = "SELECT max(mamonan) as mamonan FROM monan";
    $result = mysqli_fetch_array(mysqli_query($connection, $sql));
 
    if($result['mamonan']== null)
        return 1;
    else
        return ++$result['mamonan'];
     
    mysqli_close($connection);
}
?>