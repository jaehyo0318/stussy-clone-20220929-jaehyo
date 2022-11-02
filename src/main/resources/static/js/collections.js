class CollectionReqParam{
    static #instance = null;
    #page = 0;

    constructor() {
        this.#page = 1
    }

    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new CollectionReqParam();
        }
        return this.#instance;
    }

    getObject() {
        return {
            page: this.#page
        }
    }

    getPage() {return this.#page;}
    setPage(page) {this.#page = page;}
}

class CollectionsApi {

    static #instance = null;

    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new CollectionsApi();
        }
        return this.#instance;
    }

    getCollections(collectionReqParam) {
        const uri = location.href;
        const category = uri.substring(uri.lastIndexOf("/") + 1);
        let responseData = null;
        
        $.ajax({
            async: false,
            type: "get",
            url: "/api/collections/" + category,
            data: collectionReqParam,
            dataType: "json",
            success: (response) => {
                responseData = response.data;
                console.log(responseData);
            },
            error: (error) => {
                console.log(error);
            }
        });

        return responseData;
    }
}

class CollectionsService {
    static #instance = null;

    static getInstance() {
        if(this.#instance == null) {
                this.#instance = new CollectionsService();
        }
        return this.#instance;
    }

    gruopIdList = new Array();

    loadCollections() {
        const responseData = CollectionsApi.getInstance().getCollections(CollectionReqParam.getInstance().getObject());
    
        const collectionProducts = document.querySelector(".collection-products");
        responseData.forEach(collection => {
            this.gruopIdList.push(collection.groupId);
            collectionProducts.innerHTML += `
                <li class="collection-product">
                    <div class="product-img">
                        <img src="/image/product/${collection.imgName}">
                    </div>
                    <div class="product-name">${collection.name}</div>
                    <div class="product-price">${collection.price}원</div>
                </li>
            `;
        });
        this.addProductClickEvent();
        this.addScrollEvent();
    }

    addScrollEvent() {
        const body = document.querySelector("body");
        const html = document.querySelector("html");
        window.onscroll = () => {
            let scrollStatus = body.offsetHeight - html.clientHeight - html.scrollTop;
            if(scrollStatus > -1 && scrollStatus < 30) {
                CollectionReqParam.getInstance().setPage(Number(CollectionReqParam.getInstance().getPage()) + 1);
                CollectionsService.getInstance().loadCollections();
            }
            /* console.log("문서 높이: " + body.offsetHeight);
            console.log("html 높이: " + html.clientHeight);
            console.log("스크롤 탑: " + html.scrollTop);
            */
        }
    }

    addProductClickEvent() {
        const products = document.querySelectorAll(".collection-product");
        products.forEach((product, index) => {
            product.onclick = () => {
                console.log(this.gruopIdList[index]);
                location.href = `/products/${this.gruopIdList[index]}`;
            }
        });
    }
}

window.onload = () => {
    CollectionsService.getInstance().loadCollections();
    
}