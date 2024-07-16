package codesquad.web.snippet;

public class SnippetFixture {

    private SnippetFixture() {
    }

    public static final String LOGIN_HEADER = """
            <li class="header__menu__item">
                <div class="btn_ghost btn_size_s">%s</div>
            </li>
            <li class="header__menu__item">
                <a class="btn btn_contained btn_size_s" href="/post">글쓰기</a>
            </li>
            <li class="header__menu__item">
                <form id="logout-form" action="/user/logout">
                    <button id="logout-btn" class="btn btn_ghost btn_size_s" type="submit">로그아웃</button>
                </form>
            </li>""";

    public static final String USER_INFO = """
            <div class="user-card">
                <div class="user-header"></div>
                <div class="user-avatar"></div>
                <div class="user-info">
                    <div class="user-name">%s</div>
                    <div class="user-id">%s</div>
                    <div class="user-email">%s</div>
                </div>
            </div>""";
}
