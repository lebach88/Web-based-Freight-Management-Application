<template>
  <div class="container mt-5 text-center">
    <div class="card">
      <div class="card-body">
        <ul class="nav nav-pills mb-3 d-flex justify-content-between" role="tablist">
          <li class="nav-item flex-grow-1 text-center" role="presentation" v-for="tab in tabs" :key="tab.name">
            <button class="nav-link w-100" :class="{ active: currentTab === tab.name }" @click="currentTab = tab.name">
              <div class="label-no-color">{{ tab.title }}</div>
            </button>
          </li>
        </ul>
        <div class="tab-content pt-2">
          <component class="tab-pane fade" :class="{ 'active show': currentTab === tab.name }" v-for="tab in tabs" :is="tab.name" :key="tab.name"></component>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import {ref, onMounted, computed} from 'vue'
import axios from "axios";
import {useStore} from "vuex";
import ShippingLookup from '.././components/ShippingLookup.vue'
import FeeEstimate from '.././components/FeeEstimate.vue'
import PostOfficeSearch from '.././components/PostOfficeSearch.vue'

export default {
  name: "HomeView",
  mounted(){
    document.title = 'Trang chủ'
  },
  components: {
    ShippingLookup,
    FeeEstimate,
    PostOfficeSearch
  },
  setup(){
    const store = useStore();
    const auth = computed(() => store.state.auth)
    const currentTab = ref('ShippingLookup');
    const tabs = ref([
      { name: 'ShippingLookup', title: 'Tra cứu vận chuyển' },
      { name: 'FeeEstimate', title: 'Ước lượng cước phí ' },
      { name: 'PostOfficeSearch', title: 'Tìm kiếm bưu điện' }
    ]);
    return{
      auth,
      currentTab,
      tabs
    }
  }
}
</script>
