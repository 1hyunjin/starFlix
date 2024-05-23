import { ref } from 'vue';
import { defineStore } from 'pinia';
import { asteInfo, weatherInfo, gptReviews, meteo } from '@/api/etc';

import { httpStatusCode } from '@/util/http-status';

export const useEtcStore = defineStore(
  'etcStore',
  () => {
    const weatherList = ref([]);
    const asteList = ref([]);
    const gptReview = ref(null);
    const meteoList = ref([]);

    const aste = async (date) => {
      await asteInfo(
        date,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            asteList.value = resp.data;
          }
        },
        (error) => {
          console.log(error);
        }
      );
    };

    const weather = async (date) => {
      await weatherInfo(
        date,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            weatherList.value = resp.data;
          }
        },
        (error) => {
          console.log(error);
        }
      );
    };

    const gpt = async (idx) => {
      await gptReviews(
        idx,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            gptReview.value = resp.data;
          }
        },
        (error) => {
          console.log(error);
        }
      );
    };

    const meteoInfo = async (year) => {
      await meteo(
        year,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            meteoList.value = resp.data;
          }
        },
        (error) => {
          console.log(error);
        }
      );
    };

    return {
      weatherList,
      asteList,
      gptReview,
      meteoList,
      aste,
      weather,
      gpt,
      meteoInfo,
    };
  },
  { persist: { storage: localStorage } }
);
