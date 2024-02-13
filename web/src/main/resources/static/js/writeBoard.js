console.log("jquery");

$(document).ready(function () {
        $(document).ready(function () {
            $("#file1").on("change", handleImgFileSelect);


            //게시글 제목, 내용, 파일 업로드 ajax
            $("#btn").on("click", function (e) {
                //file
                let formData = new FormData();
                let inputFile = $("input[name='file']");
                let files = inputFile[0].files;
                console.log(files);

                for (var i = 0; i < files.length; i++) {
                    if(!checkExtension(files[i].name)){//파일의 이름과 파일의 크기
                        console.log("zip 이면 여기 들어와야해")
                        return false;
                        //!ture 라면 실패.. 그러면 위로 올라가서 "해당 종류의 파일은 업로드할 수 없습니다." 출력
                    }
                    formData.append("file", files[i]);
                }
                //board
                const title = $("#title").val();
                const content = $("#content").val();
                var data = {"title": title, "content": content, "subject": $('input[name="subject"]:checked').val()};
                console.log(data);
                console.log($('input[name="subject"]:checked').val());

                formData.append("json", new Blob([JSON.stringify(data)], {type: "application/json"}))

                $.ajax({
                    url: "/boardWrite",
                    method: "post",
                    data: formData,
                    contentType: false,
                    processData: false,
                    enctype: "multipart/form-data",
                    success: function (result) {
                        if (result == 0) {
                            alert("실패");
                        } else if (result == 9) {
                            alert("등록 성공");
                            window.location.href = "/";
                        }


                    },
                    error: function (xhr) {
                        console.log(xhr);
                    }
                });
            });
        });
    }
);

function handleImgFileSelect(e) {
    var files = e.target.files;
    var filesArr = Array.prototype.slice.call(files);

    var reg = /(.*?)\/(jpg|jpeg|png|bmp)$/;

    filesArr.forEach(function (f) {
        if (!f.type.match(reg)) {
            alert("확장자는 이미지만 가능합니다.");
            return false;
        }

        var reader = new FileReader();

        reader.onload = function (e) {
            var img = document.createElement("img");
            img.setAttribute("src", e.target.result);
            img.setAttribute("id", "img");
            document.querySelector("div.img_wrap").appendChild(img);
        };

        reader.readAsDataURL(f);
    });
}
//첨부파일의 확장자가 exe, sh, zip, alz 경우 업로드를 제한
// var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
var regex = new RegExp("(.*?)\.(jpg|jpeg|png|bmp)$");
//최대 5MB까지만 업로드 가능
var maxSize = 5242880; //5MB
//확장자 체크
function checkExtension(fileName){

    if(!regex.test(fileName)){
        alert("해당 종류의 파일은 업로드할 수 없습니다.");
        return false;
    }
    //체크 통과
    return true;
}

