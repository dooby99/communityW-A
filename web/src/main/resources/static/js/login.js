console.log("jquery");

$(document).ready(function () {
        $("button").click(function () {
            console.log("jquery");
            const name = $("#name").val();
            const pw = $("#pw").val();

            var login = {"name": name, "pw": pw}
            console.log(login);

            $.ajax({
                type: "post",
                url: "/memberLogin",
                contentType: "application/json",
                data: JSON.stringify(login),
                success: function (result) {
                    if (result == 0) {
                        alert("아이디와 비밀번호를 확인하세요.");
                        return false;
                    } else if (result == 9) {
                        alert("통신 오류입니다.")
                        return false;
                    } else {
                        alert("success!!");
                        window.location.href = "/";
                    }
                },
                error: function () {
                    alert("아이디와 비밀번호를 확인하세요.");
                }
            });
        })
    }
);

