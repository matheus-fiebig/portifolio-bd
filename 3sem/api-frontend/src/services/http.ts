import { useAuth } from '@/stores/auth';
import axios, { AxiosInstance } from 'axios';

const bearer = function(){
  const auth = useAuth().getUser();
  if(auth == undefined || auth == null || auth.token == undefined) return undefined;
  return 'Bearer ' + auth.token
};

const toggleLoading = function(display:any) {
  const el = document.getElementById('loading-interceptor');
  el!.style.display = display;
}

const axiosInstance: AxiosInstance = axios.create({
  baseURL: 'http://localhost:8080'
});

axiosInstance.interceptors.request.use(
  function (config) {
    toggleLoading("block");
    const bearerKey = bearer();
    if(bearerKey)
      config.headers.Authorization = bearerKey;
    return config;
  }, 
  function (error) {
    toggleLoading("block")
    return Promise.reject(error);
  }
);

axiosInstance.interceptors.response.use(
  function (response) {
    toggleLoading("none");
    return response;
  }, 
  function (error) {
    toggleLoading("none");
    return Promise.reject(error);
  }
);

export default axiosInstance;
