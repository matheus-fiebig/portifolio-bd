<template>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com">
  <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
  <router-view />

  <div id="loading-interceptor" class="r-modal">
    <div class="loader"></div>
  </div>

</template>

<style>
* {
  padding: 0;
  margin: 0;
  border: 0;
  list-style: none !important;
  vertical-align: baseline !important;
  text-decoration: none !important;
  font-family: 'Poppins', sans-serif;
}

a,
a:visited,
a:hover,
a:active {
  color: inherit !important;
}

#app{
  overflow-x: hidden;
  height: 100vh;
  width: 100vw;
}

.r-modal {
    display: none;
    position: fixed;
    z-index: 1;
    padding-top: 100px;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    overflow: auto;
    background-color: rgb(0, 0, 0);
    background-color: rgba(0, 0, 0, 0.4);
}

.loader {
  border: 16px solid #f3f3f3; /* Light grey */
  border-top: 16px solid #3498db; /* Blue */
  border-radius: 50%;
  width: 120px;
  height: 120px;
  animation: spin 2s linear infinite;
  margin: 15% auto;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
</style>
