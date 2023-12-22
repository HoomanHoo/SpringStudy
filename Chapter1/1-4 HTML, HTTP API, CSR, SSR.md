## Static Resource

- 고정된 HTML 파일, CSS, JS, 이미지, 영상
- 주로 웹 브라우저에서 요청

## 동적 HTML 페이지

- 동적으로 필요한 HTML 파일을 생성해서 전달
    - 어플리케이션 로직 등을 실행하고 그 결과 값을 HTML로 생성해서 응답하는 방식
    - 이 때 JSP, Thymeleaf 등의 템플릿 엔진을 사용
- 웹 브라우저는 HTML을 해석해서 화면에 표시함

## HTTP API

- HTML이 아니라 데이터를 전달
- 주로 JSON 사용
- 웹 브라우저는 JSON을 해석하지 못함 (HTML이 아니기 때문에 화면에는 JSON 형식의 데이터가 보여지게 됨)

// 결과 사진 넣는 곳

- 다양한 시스템에서 호출
- 데이터만 주고 받기 때문에 UI 화면이 필요하면 클라이언트가 별도로 처리함
- 앱, 웹 클라이언트, 서버간의 통신에서 HTTP API를 사용함
- UI 클라이언트 접점
    - 앱 클라이언트 (안드로이드 IOS PC 앱)
    - 웹 브라우저에서 자바스크립트를 통한 HTTP API 호출(Ajax, Fetch API)
    - React, Vue,js 등 자바스크립트 프레임워크
- 서버간의 통신
    - 주문 서버 → 결제 서버 와 같은 동일 서비스 내 다른 서버간 통신
    - 기업간 데이터 통신과 같은 타 서비스간 통신

## SSR, CSR

Server Side Rendering, Client Side Rendering

- SSR
    - 서버에서 HTML을 전부 생성해서 클라이언트에 전달
    - 주로 정적인 화면에 사용
    - JSP, Thymeleaf → 백엔드 개발자
    - 자바스크립트를 사용하여 화면 일부를 동적으로 변경할 수 있음
    - 예시 코드 (Fetch API를 이용한 화면 조작)
    
    ```jsx
    function paging() {
    	let modify = document.getElementById("modify").value;
    	let url = "../product/pages/" + this.id;
    	const srhKeyWord = document.getElementById("srhByName").value;
    	if (srhKeyWord !== "") {
    		if (document.getElementById("srhByName").value && modify !== "0") {
    			url = "../../product/pages/" + this.id + "?srhkeyword=" + srhKeyWord;
    		}
    		...
    
    	fetch(url).then((response) => response.json()).then((data) => {
    		const resultData = JSON.parse(data);
    
    		let pages = resultData["pages"];
    		let wines = resultData["datas"];
    		let nextPage = resultData["next_page"];
    		let prevPage = resultData["prev"];
    
    		wineList.replaceChildren();
    
    		for (var i = 0; i < wines.length; i++) {
    			let wineId = wines[i].wine_id;
    			let wineName = wines[i].wine_name;
    			...
    
    			let newRow = document.createElement("div");
    			newRow.setAttribute("class", "row");
    			let newWineInfo = document.createElement("div");
    			newWineInfo.setAttribute("id", wineName);
    			newWineInfo.setAttribute("class", "col wineName");
    			let newWineId = document.createElement("input");
    			newWineId.setAttribute("type", "hidden");
    			newWineId.setAttribute("id", wineName + 1);
    			newWineId.setAttribute("value", wineId);
    			...
    
    			wineList.appendChild(newRow);
    			newRow.appendChild(newWineInfo);
    			newWineInfo.innerText = wineName;
    			newWineInfo.appendChild(newWineId);
    			...
    
    			const wineNames = document.querySelectorAll(".wineName");
    			for (let i = 0; i < wineNames.length; i++) {
    				wineNames[i].addEventListener("click", addElement);
    			}
    		}
    		let prev = document.getElementById("prev");
    		let next = document.getElementById("next");
    		if (prevPage < 1) {
    			prev.className = "page-item disabled";
    		}
    		else {
    			prev.className = "page-item";
    			document.querySelector(".prev").setAttribute("id", pages[0] - 1);
    		}
    		...
    	})
    }
    ```
    
- CSR
    - HTML 결과를 자바스크립트를 사용해서 웹 브라우저에서 생성해서 적용함
    - 동적인 화면에 사용, 웹 환경을 앱처럼 필요한 부분만 변경할 수 있음(URL 변경, 화면 깜빡임 없이 화면 변경)
    - React, Vue.js → 웹 프론트엔드 개발
    - CSR과 SSR을 동시에 지원하는 프레임워크도 존재함
    - 예시 코드 (Vue.js)
    
    ```jsx
    import * as Vue from 'https://unpkg.com/vue@3/dist/vue.esm-browser.js';
    
    let selector = Vue.createApp({
        delimiters: ['[[', ']]'],
    
        setup() {
    
            const selected = Vue.ref(1)
            const criteriaList = Vue.ref([{ criteria: "최신순", sortValue: 1 }, { criteria: "높은 별점 순", sortValue: 2 }, { criteria: "낮은 별점 순", sortValue: 3 }])
            const reviewDatas = Vue.ref({})
    
            const changeCriteria = () => {
                const url = window.location.pathname + "/reviews?selectcode=" + selected.value
               
                fetch(url).then((response) => response.json()).then((data) => {
    
                    reviewDatas.value = JSON.parse(data)
    
                })
            }
            Vue.onMounted(() => {
                const url = window.location.pathname + "/reviews?selectcode=" + selected.value
           
                fetch(url).then((response) => response.json()).then((data) => {
                    reviewDatas.value = JSON.parse(data)
    
                })
            })
            return {
                selected, criteriaList, changeCriteria, reviewDatas
            }
        }
    })
    
    selector.mount("#selector");
    ```
    
- CSR 통신 과정
    - 빈 HTML 과 자바스크립트 링크를 클라이언트로 전달
    - 클라이언트가 자바스크립트 요청
    - 자바스크립트 (클라이언트 로직, HTML 렌더링 코드 포함) 전달
    - HTTP API (Ajax, Fetch API)로 데이터 요청
    - JSON으로 요청한 데이터 전달
    

백엔드 개발자는 서버사이드 렌더링 기술을 알아야함

- JSP, Thymeleaf
- 화면이 정적이고 복잡하지 않을 때 사용함
- 백엔드 개발자는 프론트엔드 프레임워크 기술 학습은 옵션
    - 배울 것이 그거 말고도 많다