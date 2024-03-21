<template>
    <div class="mt-3">
        <div class="card">
            <div class="card-body">
                <h3 class="card-title h3 p-2">Ước tính cước phí</h3>
                <div class="row">
                  <div class="col-6">
                    <form>
                      <div class="row bt-3">
                        <div class="col-md-6 d-flex flex-column flex-md-row mb-3">
                            <label for="startPointCity" class="col-form-label flex-grow-1">Gửi từ</label>
                            <div class="flex-grow-1">
                                <select id="startPointCity" class="form-select" aria-label="Default select example" v-model="selectedCity" @change="getDistricts">
                                    <option v-for="(city, index) in cities" :value="city.id" :key="index">{{ city.name }}</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-6 d-flex flex-column flex-md-row mb-3">
                            <label for="startPointDistrict" class="col-form-label flex-grow-1">Quận/Huyện</label>
                            <div class="flex-grow-1">
                                <select id="startPointDistrict" class="form-select" aria-label="Default select example" v-model="selectedDistrict">
                                    <option v-for="(district, index) in districts" :value="district.id" :key="index">{{ district.name }}</option>
                                </select>
                            </div>
                        </div>
                      </div>
                      <div class="row bt-3">
                        <div class="col-md-6 d-flex flex-column flex-md-row mb-3">
                          <label for="endPointCity" class="col-form-label flex-grow-1">Gửi đến</label>
                          <div class="flex-grow-1">
                            <select id="endPointCity" class="form-select" aria-label="Default select example" v-model="selectedCityEnd" @change="getDistrictsEnd">
                              <option v-for="(city, index) in citiesEnd" :value="city.id" :key="index">{{ city.name }}</option>
                            </select>
                          </div>
                        </div>
                        <div class="col-md-6 d-flex flex-column flex-md-row mb-3">
                          <label for="endPointDistrict" class="col-form-label flex-grow-1">Quận/Huyện</label>
                            <div class="flex-grow-1">
                              <select id="endPointDistrict" class="form-select" aria-label="Default select example" v-model="selectedDistrictEnd">
                                <option v-for="(district, index) in districtsEnd" :value="district.id" :key="index">{{ district.name }}</option>
                              </select>
                            </div>
                        </div>
                      </div>
                      <div class="row mb-3">
                        <label for="inputText" class="col-sm-2 col-form-label">Trọng lượng (Gram)</label>
                        <div class="col-sm-10">
                          <input type="text" class="form-control">
                        </div>
                      </div>
                      <div class="row mb-3">
                        <label for="inputText" class="col-sm-2 col-form-label">Tiền thu hộ (VND)</label>
                        <div class="col-sm-10">
                          <input type="text" class="form-control">
                        </div>
                      </div>
                    </form>
                  </div>
                  <div class="col-6">
                    <img src="../assets/image/map.png" alt="Tra cứu vận chuyển" class="img-fluid">
                  </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'https://vnprovinces.pythonanywhere.com/api/provinces/', 
  withCredentials: false, 
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json'
  }
});

export default {
  name: 'FeeEstimate',
  data() {
    return {
      cities: [],
      districts: [],
      selectedCity: null,
      selectedDistrict: null,
      citiesEnd: [],
      districtsEnd: [],
      selectedCityEnd: null,
      selectedDistrictEnd: null
    };
  },
  methods: {
    getProvinces() {
      apiClient.get('/?basic=true&limit=100').then(response => {
        this.cities = response.data.results;
        this.citiesEnd = response.data.results;
      });
    },
    getDistricts() {
      if (this.selectedCity) {
        apiClient.get(`/${this.selectedCity}`).then(response => {
          this.districts = response.data.districts;
        });
      }
    },
    getDistrictsEnd() {
      if (this.selectedCityEnd) {
        apiClient.get(`/${this.selectedCityEnd}`).then(response => {
          this.districtsEnd = response.data.districts;
        });
      }
    }
  },
  created() {
    this.getProvinces();
  }
};
</script>
