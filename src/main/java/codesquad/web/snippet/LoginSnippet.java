package codesquad.web.snippet;

public class LoginSnippet {

    private LoginSnippet() {
    }

    public static final String NO_LOGIN_HEADER = """
            <li class="header__menu__item">
                <a class="btn btn_contained btn_size_s" href="/user/login">로그인</a>
            </li>
            <li class="header__menu__item">
                <a class="btn btn_ghost btn_size_s" href="/registration">
                    회원 가입
                </a>
            </li>""";
    public static final String LOGIN_HEADER = """
            <li class="header__menu__item">
                <div class="btn_ghost btn_size_s">%s</div>
            </li>
            <li class="header__menu__item">
                <a class="btn btn_contained btn_size_s" href="/article">글쓰기</a>
            </li>
            <li class="header__menu__item">
                <form id="logout-form" action="/user/logout">
                    <button id="logout-btn" class="btn btn_ghost btn_size_s" type="submit">로그아웃</button>
                </form>
            </li>""";
}
