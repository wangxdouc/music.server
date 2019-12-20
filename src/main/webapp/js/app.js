var myVue = new Vue({
  el: '#app',
  data () {
    return {
      info: ""
    }
  },
  mounted () {
    axios.get('http://service.uspacex.com/music.server/queryMusicSheets?type=top20')
    .then(function (response) {
    	console.log(response);
    	this.info = response;
    }).catch(function (error) { 
    	console.log(error);
    });
  }
})