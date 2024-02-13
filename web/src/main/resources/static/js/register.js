console.log("jquery");

$(document).ready(function () {
        $("#btn").click(function () {
            console.log("jquery");
            const name = $("#name").val();
            const pw = $("#pw").val();
            const pw2 = $("#pw2").val();

            var login = {"name": name, "pw": pw, "pw2": pw2};
            console.log(login);

            $.ajax({
                type: "post",
                url: "/memberRegister",
                contentType: "application/json",
                data: JSON.stringify(login),
                success: function (result) {
                    if (result == 0) {
                        alert("비밀번호를 확인하세요..");
                        return false;
                    } else if (result == 1) {
                        alert("중복된 아이디 입니다")
                        return false;
                    } else if (result == 9) {
                        alert("singUp success!!");
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

