# Java WAS

2024 우아한 테크캠프 프로젝트 WAS

- Java
    - Temurin 17.0.11
    - Gradle 8.5
- 실행방법
    - codesquad.Main.main() 실행

<br><br>

## HiiWee's WAS Flow

### 1. 사용자 요청 Multi Thread 분기

<img src="image/multi_thread.png" alt="img.png" style="width:80%;height:auto;">

- `ThreadPoolExecutor`를 통해 쓰레드 풀을 만들고 각 요청에 대해 스레드 풀의 스레드로 연결합니다.
- 이후 각 요청마다 `HttpRequest`, `HttpResponse` 객체가 만들어지고 요청을 독립적으로 처리할 수 있도록 합니다.

<br>

### 2. `ConnectionHandler`가 요청에 따른 `RequestHandler`를 찾는다.

<img src="image/find_request_handler.png" alt="img.png" style="width:80%;height:auto;">

- `ConnectionHandler`는 현재 `HttpRequest`를 처리할 수 있는 핸들러를 `요청 경로`를 통해 `RequestHandler`를 찾습니다.

<br>

### 3. `RequestHandler`의 요청 처리

<img src="image/request_handler.png" alt="img.png" style="width:100%;height:auto;">

- `RequestHandler`에게 HttpRequest, HttpResponse를 넘겨주면서 처리를 요청합니다.
- `RequestHandler`는 비즈니스 로직을 처리합니다.
- `RequestHandler`는 HttpResponse에게 redirect 혹은 forward 처리를 요청합니다.
- `HttpResponse`는 브라우저에게 응답을 반환하여 하나의 사용자 요청이 마무리 됩니다.
