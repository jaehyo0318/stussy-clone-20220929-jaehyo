class ImportApi{
    static #instance = null;
    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new ImportApi();
        }
        return this.#instance;
    }

    IMP = null;

    #importPayParams = {
        pg: "kakaopay",
        pay_method: "card",
        merchant_uid: "product" + new Date().getTime(),
        name: "노르웨이 회전 의자",
        amount: 1,
        buyer_email: "gildong@gmail.com",
        buyer_name: "홍길동",
        buyer_tel: "010-4242-4242",
        buyer_addr: "서울특별시 강남구 신사동",
        buyer_postcode: "01181"
    }

    impInfo = {
        impUid: null,
        restApiKey: null,
        restApiSecret: null
    }

    constructor() {
        this.IMP = window.IMP;
        this.impInfo.impUid = "imp27440007";
        this.impInfo.restApiKey = "8316668500852040";
        this.impInfo.restApiSecret = "bD0AKuenRq9zkKIZUxgPCKfHrhiXVx6NRkXCU5snC0obC9UumeG4JoGZ3isSC4vUJxfXaLHJxpNDUXZz";

        this.IMP.request_pay(this.impInfo.impUid);
    }

    requestPay() {
        this.IMP.request_pay(this.#importPayParams, this.responsePay);
    }

    responsePay(resp) {
        if(resp.sucess) {
            alert("결제 성공");
        }else {
            alert("결제 실패");
        }
    }

    requestImpAccessToken() {
        const accessToken = null;

        $.ajax({
            async: false,
            type: "post",
            url:"https://api.iamport.kr/users/getToken",
            contentType: "application/json",
            data:JSON.stringify({
                imp_key: this.impInfo.restApiKey,
                imp_secret: this.impInfo.restApiSecret 
            }),
            dataType: "json",
            success: (response) => {
                accessToken = response;
            },
            error: (error) => {
                console.log(error);
            }
        });

        return accessToken;
    }

    requestPayDetails() {
        const accessToken = this.requestImpAccessToken();
        console.log(accessToken);
    }
}

class Order {

    constructor() {
        this.addPaymentButtonEvent();
    }

    addPaymentButtonEvent() {
        const paymentButton = document.querySelector(".payment-button");
        paymentButton.onclick = () => {
            ImportApi.getInstance().requestPay();
        }
    }
}

window.onload = () => {
    AddressApi.getInstance().addressButtonEvent();
    new Order();
}