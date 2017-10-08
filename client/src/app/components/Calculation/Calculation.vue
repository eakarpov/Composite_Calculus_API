<template xmlns:v-on="http://www.w3.org/1999/xhtml">
    <div>
        <h2>This is our calculation div</h2>
        <form v-on:submit="formListener">
            <textarea id="calculation" title="calculation">
            </textarea>
            <br/>
            <button type="submit">{{buttonName}}</button>
        </form>
    </div>
</template>
<script src="./Calculation.ts" lang="ts"></script>