<script setup lang="ts">
defineProps<{
  msg: string;
}>();
</script>

<script lang="ts">
export default {
  data() {
    return {
      events: [] as object[]
    };
  },
  methods: {
    getPosts() {
      let url = "/api/sse/n111";
      let eventSource = new EventSource(url);
      console.log(eventSource);

      eventSource.onmessage = (event) => {
        console.log(event.data);
        const data = JSON.parse(event.data);
        this.events = [...this.events, data];
      };
    },
  },
};
</script>

<template>
  <div class="greetings">
    <h1 class="green">{{ msg }}</h1>
    <h3>
      You’ve successfully created a project with
      <a href="https://vitejs.dev/" target="_blank" rel="noopener">Vite</a> +
      <a href="https://vuejs.org/" target="_blank" rel="noopener">Vue 3</a>.

      <button @click="getPosts">Show Posts</button>

      <ul>
        <li v-for="item in events">
          {{ item }}
        </li>
      </ul>

    </h3>
  </div>
</template>

<style scoped>
h1 {
  font-weight: 500;
  font-size: 2.6rem;
  top: -10px;
}

h3 {
  font-size: 1.2rem;
}

.greetings h1,
.greetings h3 {
  text-align: center;
}

@media (min-width: 1024px) {
  .greetings h1,
  .greetings h3 {
    text-align: left;
  }
}
</style>
