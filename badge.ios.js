
export default {
  launcher: null,
  supported: true,

  setCount (count) {
    // check permission here?
    return Promise.resolve(true)
  },

  getCount () {
    return Promise.resolve(0)
  },
}
