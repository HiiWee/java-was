package codesquad.web.snippet;

public class SnippetFixture {

    private SnippetFixture() {
    }

    public static final String NONE_LOGIN_HEADER = """
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
                <img src="/image?imageName=%s" class="user-avatar">
                <div class="user-info">
                    <div class="user-name">%s</div>
                    <div class="user-id">%s</div>
                    <div class="user-email">%s</div>
                </div>
            </div>""";

    public static final String POST_INFO = """
            <div class="post">
                <div class="post__account">
                    <img src="/image?imageName=%s" class="post__account__img"/>
                    <p class="post__account__nickname">%s</p>
                    <h1>%s</h1>
                </div>
                <img src="/image?imageName=%s" class="post__img"/>
                <div class="post__menu">
                    <ul class="post__menu__personal">
                        <li>
                            <button class="post__menu__btn">
                                <img src="./img/like.svg"/>
                            </button>
                        </li>
                        <li>
                            <button class="post__menu__btn">
                                <img src="./img/sendLink.svg"/>
                            </button>
                        </li>
                    </ul>
                    <button class="post__menu__btn">
                        <img src="./img/bookMark.svg"/>
                    </button>
                </div>
                <p class="post__article">
                    %s
                </p>
            </div>""";

    public static final String COMMENT_WRAPPER = """
            <ul class="comment">
                %s
            </ul>
            <form class="form" action="/post/comment" method="post">
                <div class="textfield textfield_size_m" style="height: auto">
                    <input type="hidden" name="postId" value="%s">
                    <textarea
                            class="input_textfield"
                            placeholder="댓글을 작성하세요"
                            autocomplete="username"
                            name="content"
                    ></textarea>
                </div>
                <button
                        id="registration-btn"
                        class="btn btn_contained btn_size_s"
                        style="margin-top: 24px"
                        type="submit"
                >
                    댓글 작성
                </button>
            </form>""";

    public static final String COMMENT_INFO = """
            <li class="comment__item">
                <div class="comment__item__user">
                    <img src="/image?imageName=%s"class="comment__item__user__img"/>
                    <p class="comment__item__user__nickname">%s</p>
                </div>
                <p class="comment__item__article">
                    %s
                </p>
            </li>""";

}
