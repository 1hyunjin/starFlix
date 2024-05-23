import { createRouter, createWebHistory } from 'vue-router';
import FindView from '@/views/LoginBefore/FindView.vue';
import LoginView from '@/views/LoginBefore/LoginView.vue';
import MainView from '@/views/LoginBefore/MainView.vue';
import SplashView from '@/views/LoginBefore/SplashView.vue';

// 홈화면
import HomeView from '@/views/LoginAfter/HomeView.vue';
// 별명소 지도
import MapView from '@/views/MapView.vue';
import AddPlace from '@/component/map/AddPlace.vue';
import MapInfoDiv from '@/component/map/MapInfoDiv.vue';
import Around from '@/component/map/Around.vue';
import AroundItem from '@/component/map/AroundItem.vue';
import Maphome from '@/component/map/Maphome.vue';
import PlaceInfo from '@/component/map/PlaceInfo.vue';
import Review from '@/component/map/Review.vue';
import ReviewList from '@/component/map/ReviewList.vue';
import ReadReview from '@/component/map/ReadReview.vue';
import ModifyReview from '@/component/map/ModifyReview.vue';
import AddPlaceView from '@/views/AddPlaceView.vue';

// 커뮤니티
import BoardView from '@/views/Board/BoardView.vue';
import List from '@/component/board/List.vue';
import Write from '@/component/board/Write.vue';
import Read from '@/component/board/Read.vue';
import Modify from '@/component/board/Modify.vue';

// 찜리스트
import MyPickView from '@/views/Pick/MyPickView.vue';

// 마이페이지
import MyPageView from '@/views/MyPageView.vue';
import MyInfo from '@/component/MyPage/MyInfo.vue';
import MyPlace from '@/component/MyPage/MyPlace.vue';
import MyReview from '@/component/MyPage/MyReview.vue';

import modal from '@/views/LoginAfter/modal.vue';

import { storeToRefs } from 'pinia';
import { useMapStore } from '@/stores/mapStore';
import { useUserStore } from '@/stores/user';
import { useBoardStore } from '@/stores/boardStore';

const loginBefore = async (to, from, next) => {
  const userStore = useUserStore();
  const { isLogin } = storeToRefs(userStore);
  if (!isLogin.value) {
    alert('로그인 후 이용 가능합니다.');
    next({ name: 'main' });
  } else {
    next();
  }
};
const loginAfter = async (to, from, next) => {
  const userStore = useUserStore();
  const { isLogin } = storeToRefs(userStore);
  if (isLogin.value) {
    alert('잘못된 접근입니다.');
    next({ name: 'home' });
  } else {
    next();
  }
};

const OnlyAuthUser = async (to, from, next) => {
  const userStore = useUserStore();
  const { userInfo, isValidToken } = storeToRefs(userStore);
  const { getUserInfo } = userStore;

  let token = sessionStorage.getItem('accessToken');

  if (userInfo.value != null && token) {
    await getUserInfo(token);
  }
  if (!isValidToken.value || userInfo.value === null) {
    alert('로그인 후 이용가능합니다.');
    next({ name: 'main' });
  } else {
    next();
  }
};

const isArrowAround = (val) => {
  const mapStore = useMapStore();
  const { isAround } = storeToRefs(mapStore);

  if (val) {
    isAround.value = true;
  } else {
    isAround.value = false;
  }
};
const isArrowSearch = (val) => {
  const mapStore = useMapStore();
  const { isSearch } = storeToRefs(mapStore);

  if (val) {
    isSearch.value = true;
  } else {
    isSearch.value = false;
  }
};
const isArrowResult = (val) => {
  const mapStore = useMapStore();
  const { myLocation, mapInfo, isResult } = storeToRefs(mapStore);
  if (val) {
    isResult.value = true;
  } else {
    isResult.value = false;
  }
};

// const userStore = useUserStore();
// const boardStore = useBoardStore();

// const { place, isSearch, isAround, isResult } = storeToRefs(mapStore);

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/modal',
      name: 'modal',
      component: modal,
    },
    {
      path: '/',
      name: 'splash',
      beforeEnter: (to, from, next) => {
        const userStore = useUserStore();
        const { isLogin } = storeToRefs(userStore);

        if (!isLogin.value) {
          next({ name: 'main' });
        } else {
          next();
        }
      },
      component: SplashView,
    },
    {
      path: '/',
      name: 'main',
      beforeEnter: (to, from, next) => {
        const userStore = useUserStore();
        const { isLogin, isValidToken } = storeToRefs(userStore);
        const { logout } = userStore;

        if (isLogin.value) {
          if (isValidToken.value) {
            next({ name: 'splash' });
          } else {
            logout();
          }
        } else {
          next();
        }
      },
      component: MainView,
    },
    {
      path: '/login',
      name: 'login',
      beforeEnter: loginAfter,
      component: LoginView,
    },
    {
      path: '/regist',
      name: 'regist',
      beforeEnter: loginAfter,
      component: LoginView,
    },
    {
      path: '/find',
      name: 'find',
      beforeEnter: loginAfter,
      component: FindView,
    },

    // 여기까지 로그인 전
    {
      path: '/addPlace',
      name: 'addPlace',
      beforeEnter: OnlyAuthUser,
      component: AddPlaceView,
    },
    {
      path: '/home',
      name: 'home',
      beforeEnter: OnlyAuthUser,
      component: HomeView,
    },
    {
      //일반 게시판리스트
      path: '/board',
      name: 'boardList',
      redirect: '/board/list',
      component: BoardView,
      children: [
        {
          path: 'list',
          name: 'list',
          beforeEnter: OnlyAuthUser,
          component: List,
        },
        {
          path: 'write',
          name: 'write',
          beforeEnter: OnlyAuthUser,
          component: Write,
        },
        {
          path: 'read',
          name: 'read',
          beforeEnter: OnlyAuthUser,
          component: Read,
        },
        {
          path: 'modify',
          name: 'modify',
          beforeEnter: OnlyAuthUser,
          component: Modify,
        },
      ],
    },
    {
      // 찜리스트
      path: '/pick',
      name: 'pick',
      beforeEnter: OnlyAuthUser,
      component: MyPickView,
    },
    {
      //마이페이지
      path: '/mypage',
      name: 'mypage',
      redirect: '/mypage/userInfo',
      component: MyPageView,
      children: [
        {
          path: 'userInfo',
          name: 'userInfo',
          beforeEnter: OnlyAuthUser,
          component: MyInfo,
        },
        {
          path: 'myPlaces',
          name: 'myPlaces',
          beforeEnter: OnlyAuthUser,
          component: MyPlace,
        },
        {
          path: 'myReviews',
          name: 'myReviews',
          beforeEnter: OnlyAuthUser,
          component: MyReview,
        },
      ],
    },
    {
      path: '/map',
      name: 'map',
      redirect: '/map/mapHome',
      component: MapView,
      children: [
        {
          path: 'mapHome',
          name: 'mapHome',
          component: Maphome,
          beforeEnter: (to, from, next) => {
            OnlyAuthUser();
            // 페이지 이동 전에 수행할 작업
            isArrowResult(true);
            isArrowSearch(false);
            console.log('지도검색');

            // 작업 완료 후 페이지 이동
            next();
          },
        },
        {
          path: 'mapInfo',
          name: 'mapInfo',
          redirect: '/map/mapInfo/placeInfo',
          component: MapInfoDiv,
          children: [
            {
              path: 'placeInfo',
              name: 'placeInfo',
              beforeEnter: OnlyAuthUser,
              component: PlaceInfo,
            },
            {
              path: 'Review',
              name: 'Review',
              beforeEnter: OnlyAuthUser,
              component: Review,
            },
            {
              path: 'placeReview',
              name: 'placeReview',
              beforeEnter: OnlyAuthUser,
              component: ReviewList,
            },
            {
              path: 'ModifyReview',
              name: 'ModifyReview',
              beforeEnter: OnlyAuthUser,
              component: ModifyReview,
            },
            {
              path: 'placeAround',
              name: 'placeAround',
              redirect: '/map/mapInfo/placeAround/tour',
              beforeEnter: (to, from, next) => {
                OnlyAuthUser();
                // 페이지 이동 전에 수행할 작업
                isArrowAround(true);
                console.log('주변관광');

                // 작업 완료 후 페이지 이동
                next();
              },
              component: Around,
              children: [
                {
                  path: 'tour',
                  name: 'tour',
                  beforeEnter: OnlyAuthUser,
                  component: AroundItem,
                },
                {
                  path: 'culture',
                  name: 'culture',
                  beforeEnter: OnlyAuthUser,
                  component: AroundItem,
                },
                {
                  path: 'festival',
                  name: 'festival',
                  beforeEnter: OnlyAuthUser,
                  component: AroundItem,
                },
                {
                  path: 'travel',
                  name: 'travel',
                  beforeEnter: OnlyAuthUser,
                  component: AroundItem,
                },
                {
                  path: 'Leisure',
                  name: 'Leisure',
                  beforeEnter: OnlyAuthUser,
                  component: AroundItem,
                },
                {
                  path: 'sleep',
                  name: 'sleep',
                  beforeEnter: OnlyAuthUser,
                  component: AroundItem,
                },
                {
                  path: 'shop',
                  name: 'shop',
                  beforeEnter: OnlyAuthUser,
                  component: AroundItem,
                },
                {
                  path: 'food',
                  name: 'food',
                  beforeEnter: OnlyAuthUser,
                  component: AroundItem,
                },
                {
                  path: 'camp',
                  name: 'camp',
                  beforeEnter: OnlyAuthUser,
                  component: AroundItem,
                },
              ],
            },
            {
              path: 'readReview',
              name: 'readReview',
              beforeEnter: OnlyAuthUser,
              component: ReadReview,
            },
          ],
        },
        // {
        //   path: 'addplace',
        //   name: 'addplace',
        //   component: AddPlace,
        // },
      ],
    },
  ],
});

// 라우터 전 각 네비게이션마다 실행됩니다.
router.beforeEach((to, from, next) => {
  if (!to.matched.length) {
    // 일치하는 라우트가 없으면
    alert('페이지를 찾을 수 없습니다.'); // 경고 메시지 표시
    // 이전 페이지로 돌아가기
    if (from.name) {
      next({ name: from.name });
    } else {
      next('/'); // 이전 페이지가 없는 경우 홈 페이지로 이동합니다.
    }
  } else {
    next(); // 다음으로 진행
  }
});

export default router;
