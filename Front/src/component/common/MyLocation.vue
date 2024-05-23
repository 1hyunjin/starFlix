<template>
  <div>
    <p v-if="location">당신의 위치는 {{ location }} 입니다.</p>
    <p v-else>위치 정보를 가져오는 중입니다...</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

const location = ref(null);

onMounted(() => {
  if ('geolocation' in navigator) {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        const { latitude, longitude } = position.coords;

        // Google Maps Geocoding API를 호출하여 위도와 경도를 주소로 변환
        fetch(
          `https://maps.googleapis.com/maps/api/geocode/json?latlng=${latitude},${longitude}&key=YOUR_API_KEY`
        )
          .then((response) => response.json())
          .then((data) => {
            if (data.results && data.results[0]) {
              location.value = data.results[0].formatted_address;
            } else {
              console.error('주소를 가져오는 데 실패했습니다.');
            }
          })
          .catch((error) => {
            console.error('주소를 가져오는 데 실패했습니다.', error);
          });
      },
      (error) => {
        // 위치 정보를 가져오는 데 실패한 경우
        console.error('위치 정보를 가져오는 데 실패했습니다.', error);
      }
    );
  } else {
    console.error('이 브라우저는 Geolocation API를 지원하지 않습니다.');
  }
});
</script>
