<template>
    <div class="createria-form" :id="criteriaFormValue.criteriaCard.id">
        <n-spin :show="loading" :rotate="false">
            <template #icon>
                <n-icon>
                    <BounceLoader
                        :loading="loading"
                        color="#0954AB"
                        size="60px" />
                </n-icon>
            </template>
            <n-scrollbar style="height: 100%; background-color: #F1F1F1; border-radius: 5px;">
                <div class="form-content">
                    <n-form ref="formRef" size="small" label-placement="left" label-width="160" :model="criteriaFormValue" :rules="rules" require-mark-placement="right-hanging">
                        <n-form-item style="font-size: 12px; --n-feedback-height: 20px" label-style="font-size: 12px;font-weight: bolder;" label="PMID/PMCID/DOI" path="criteriaCard.pmid" >
                            <n-input-group style="width: 94%;">
                                <n-input placeholder="Input pmid, pmcid, doi" v-model:value="criteriaFormValue.criteriaCard.pmid" style="font-size: 12px;" disabled />
                                <n-button type="primary" style="background-color: #7795BB;" @click="toArticleLink">View on article page</n-button>
                            </n-input-group>
                        </n-form-item>
                        <n-form-item v-if="props.info.name !=='PM3' && props.info.name !=='PP1' && props.info.name !=='PS4' && props.info.name !=='PS2' && props.info.name !=='PM6' " label-style="font-size: 12px; width: 50%;font-weight: bolder;" label="Strength" style="--n-feedback-height: 20px" path="criteriaCard.strength">
                            <n-radio-group style="width: 94%;" v-model:value="criteriaFormValue.criteriaCard.strength">
                                <n-radio-button class="radio-btn radio-btn-left" label='Supporting' value='Supporting' :style="criteriaFormValue.criteriaCard.strength === 'Supporting' ? 'background-color: #FFC300; color: #ffffff;' : ''"></n-radio-button>
                                <n-radio-button class="radio-btn" label='Moderate' value='Moderate' :style="criteriaFormValue.criteriaCard.strength === 'Moderate' ? 'background-color: #FF8D1A; color: #ffffff;' : ''"></n-radio-button>
                                <n-radio-button class="radio-btn" label='Strong' value='Strong' :style="criteriaFormValue.criteriaCard.strength === 'Strong' ? 'background-color: #12990F; color: #ffffff;' : ''"></n-radio-button>
                                <n-radio-button class="radio-btn" label='VeryStrong' value='VeryStrong' :style="criteriaFormValue.criteriaCard.strength === 'VeryStrong' ? 'background-color: #D43030; color: #ffffff;' : ''"></n-radio-button>
                                <n-radio-button class="radio-btn radio-btn-right" label='Unmet' value='Unmet' :style="criteriaFormValue.criteriaCard.strength === 'Unmet' ? 'background-color: #383838; color: #ffffff;' : ''"></n-radio-button>
                            </n-radio-group>
                        </n-form-item>
                        <div v-if="props.info.name==='PM3' || props.info.name==='BP2'">
                            <n-form-item label="Phase" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 20px" path="pm3Criteria.phase" >
                                <div v-if="props.info.name==='PM3'">
                                    <n-radio-group style="width: 94%;" v-model:value="criteriaFormValue.pm3Criteria.phase">
                                        <n-radio-button class="radio-btn radio-btn-left" style="width: 65px" label='In trans' value='In trans' :style="criteriaFormValue.pm3Criteria.phase === 'In trans' ? 'background-color: #2A82E4; color: #ffffff;' : ''"></n-radio-button>
                                        <n-radio-button class="radio-btn" style="width: 120px" label='Homozygosity' value='Homozygosity' :style="criteriaFormValue.pm3Criteria.phase === 'Homozygosity' ? 'background-color: #2A82E4; color: #ffffff;' : ''"></n-radio-button>
                                        <n-radio-button class="radio-btn" style="width: 300px" label='Homozygosity in consanguineous individual(s)' value='Homozygosity in consanguineous individual(s)' :style="criteriaFormValue.pm3Criteria.phase === 'Homozygosity in consanguineous individual(s)' ? 'background-color: #2A82E4; color: #ffffff;' : ''"></n-radio-button>
                                        <n-radio-button class="radio-btn radio-btn-right" style="width: 80px" label='Unknown' value='Unknown' :style="criteriaFormValue.pm3Criteria.phase === 'Unknown' ? 'background-color: #2A82E4; color: #ffffff;' : ''"></n-radio-button>
                                    </n-radio-group>
                                </div>
                                <div v-if="props.info.name === 'BP2'">
                                    <n-radio-group style="width: 94%;" v-model:value="criteriaFormValue.pm3Criteria.phase">
                                        <n-radio-button class="radio-btn radio-btn-left" label='In cis' value='In cis' :style="criteriaFormValue.pm3Criteria.phase === 'In cis' ? 'background-color: #2A82E4; color: #ffffff;' : ''"></n-radio-button>
                                        <n-radio-button class="radio-btn radio-btn-right" label='In trans' value='In trans' :style="criteriaFormValue.pm3Criteria.phase === 'In trans' ? 'background-color: #2A82E4; color: #ffffff;' : ''"></n-radio-button>
                                    </n-radio-group>
                                </div>
                            </n-form-item>
                            <n-form-item label="Variant 2" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 20px" :path="(criteriaFormValue.pm3Criteria.phase === 'Homozygosity' || criteriaFormValue.pm3Criteria.phase === 'Homozygosity in consanguineous individual(s)') ? 'variant1' : 'pm3Criteria.allele2'" :ignore-path-change="false">
                                <n-input-group style="width: 94%;">
                                    <n-input style="font-size: 12px;" v-model:value="criteriaFormValue.pm3Criteria.allele2" placeholder="Input Variant" :disabled="(criteriaFormValue.pm3Criteria.phase === 'Homozygosity' || criteriaFormValue.pm3Criteria.phase === 'Homozygosity in consanguineous individual(s)') ? true : false" />
                                </n-input-group>
                            </n-form-item>
                            <n-form-item label="ClinVar pathogenicity" v-if="props.info.name !=='BP2'" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 11px">
                                <n-input-group style="width: 94%;">
                                    <div style="display: flex; align-items: center;">
                                        <div style="font-weight: bolder;color: #D43030;margin-right: 10px;">{{ criteriaFormValue.pm3Criteria.clinvarPathogenicity }}</div>
                                        <div>
                                            <n-button style="border-radius: 8px; background-color: #6C5C91; color: #ffffff; font-size: 12px; --n-border: none;">Clinical Evidence</n-button>
                                        </div>
                                    </div>
                                </n-input-group>
                            </n-form-item>
                            <n-form-item label="Variant 2 pathogenicity" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 20px" :path="(criteriaFormValue.pm3Criteria.phase === 'Homozygosity' || criteriaFormValue.pm3Criteria.phase === 'Homozygosity in consanguineous individual(s)') ? 'variant1' : 'pm3Criteria.pathogenicity'">
                                <n-radio-group style="width: 94%;" v-model:value="criteriaFormValue.pm3Criteria.pathogenicity" :disabled="(criteriaFormValue.pm3Criteria.phase === 'Homozygosity' || criteriaFormValue.pm3Criteria.phase === 'Homozygosity in consanguineous individual(s)')">
                                    <n-radio-button class="radio-btn variant-btn radio-btn-left" label='Pathogenic' value='Pathogenic' :style="criteriaFormValue.pm3Criteria.pathogenicity === 'Pathogenic' ? 'background-color: #D43030; color: #ffffff;' : ''"></n-radio-button>
                                    <n-radio-button class="radio-btn variant-btn" style="width: 130px" label='Likely Pathogenic' value='Likely Pathogenic' :style="criteriaFormValue.pm3Criteria.pathogenicity === 'Likely Pathogenic' ? 'background-color: #FF5733; color: #ffffff;' : ''"></n-radio-button>
                                    <n-radio-button class="radio-btn variant-btn" style="width: 60px" label='VUS' value='VUS' :style="criteriaFormValue.pm3Criteria.pathogenicity === 'VUS' ? 'background-color: #424242; color: #ffffff;' : ''"></n-radio-button>
                                    <n-radio-button class="radio-btn variant-btn" label='Likely Benign' value='Likely Benign' :style="criteriaFormValue.pm3Criteria.pathogenicity === 'Likely Benign' ? 'background-color: #43CF7C; color: #ffffff;' : ''"></n-radio-button>
                                    <n-radio-button class="radio-btn variant-btn radio-btn-right" style="width: 80px" label='Benign' value='Benign' :style="criteriaFormValue.pm3Criteria.pathogenicity === 'Benign' ? 'background-color: #12990F; color: #ffffff;' : ''"></n-radio-button>
                                </n-radio-group>
                            </n-form-item>
                            <n-form-item label="Proband number" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 20px" path="criteriaCard.proband">
                                <n-input-group style="width: 94%;">
                                    <n-input-number style="--n-font-size: 12px; font-size: 12px;" size="small" min="0" v-model:value="criteriaFormValue.criteriaCard.proband" clearable />
                                </n-input-group>
                            </n-form-item>
                        </div>
                        <div v-if="props.info.name==='PS4'">
                            <n-form-item label="Phenotype" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 20px" path="criteriaCard.phenotype">
                                <n-input-group style="width: 94%;">
                                    <n-select size="small" v-model:value="criteriaFormValue.criteriaCard.phenotype" :options="phenotypeOptions" />
                                </n-input-group>
                            </n-form-item>
                            <n-form-item label="Ancestry" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 20px" path="ps4Criteria.ancestry">
                                <n-input-group style="width: 94%;">
                                    <n-select size="small" v-model:value="criteriaFormValue.ps4Criteria.ancestry" :options="ancestryOptions" />
                                </n-input-group>
                            </n-form-item>
                            <n-form-item label="abc" label-width="160" label-style="font-size: 12px;font-weight: bolder; color: rgba(0,0,0,0)" style="--n-feedback-height: 0px" path="">
                                <n-input-group style="width: 94%;">
                                    <div class="ps4-fre">
                                        <div class="fre-child">Mutant allele</div>
                                        <div class="fre-child">Total allele</div>
                                        <div class="fre-child">Allele frequency</div>
                                    </div>
                                </n-input-group>
                            </n-form-item>
                            <n-form-item label="Patient(s)" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 5px" path="ps4Criteria.patientFreq">
                                <n-input-group style="width: 94%;">
                                    <div class="ps4-fre">
                                        <div class="fre-child"><n-input-number placeholder="0" style="--n-font-size: 12px; font-size: 12px;" size="small" v-model:value="criteriaFormValue.ps4Criteria.alleleInPatient" @blur="calculatorFreq('patients')" min="0" clearable /></div>
                                        <div class="fre-child"><n-input-number placeholder="0" style="--n-font-size: 12px; font-size: 12px;" size="small" v-model:value="criteriaFormValue.ps4Criteria.totalAlleleInPatient" @blur="calculatorFreq('patients')" min="0" clearable /></div>
                                        <div class="fre-child"><n-input class="fre-child-input" placeholder="" v-model:value="patientFreq" disabled />%</div>
                                    </div>
                                </n-input-group>
                            </n-form-item>
                            <n-form-item label="Control(s)" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 5px" path="ps4Criteria.controlFreq">
                                <n-input-group style="width: 94%;">
                                    <div class="ps4-fre">
                                        <div class="fre-child"><n-input-number placeholder="0" style="--n-font-size: 12px; font-size: 12px;" size="small" v-model:value="criteriaFormValue.ps4Criteria.alleleInControl" @blur="calculatorFreq('controls')" min="0" clearable /></div>
                                        <div class="fre-child"><n-input-number placeholder="0" style="--n-font-size: 12px; font-size: 12px;" size="small" v-model:value="criteriaFormValue.ps4Criteria.totalAlleleInControl" @blur="calculatorFreq('controls')" min="0" clearable /></div>
                                        <div class="fre-child"><n-input class="fre-child-input" placeholder="" v-model:value="controlFreq" disabled />%</div>
                                    </div>
                                </n-input-group>
                            </n-form-item>
                            <n-form-item label="text" label-style="font-size: 12px;font-weight: bolder;color: rgba(0,0,0,0)" :show-require-mark="false" style="--n-feedback-height: 20px" :path="!criteriaFormValue.ps4Criteria.oddRatio ? 'ps4Criteria.oddRatio' : ''">
                                <n-input-group style="width: 94%;">
                                    <div class="ps4-caculator">
                                        <div class="cac-child">
                                            <div>Odd ratio</div>
                                            <div><n-input class="fre-child-input" placeholder="" v-model:value="oddRatioFreq" disabled /></div>
                                        </div>
                                        <div class="cac-child">
                                            <div>95% confidence interval</div>
                                            <div><n-input class="fre-child-input" style="width: 200px" placeholder="" v-model:value="criteriaFormValue.ps4Criteria.confidenceInterval" disabled /></div>
                                        </div>
                                    </div>
                                </n-input-group>
                            </n-form-item>
                            <n-form-item label="Inheritance" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 20px" path="criteriaCard.inheritance">
                                <n-radio-group style="width: 94%;" v-model:value="criteriaFormValue.criteriaCard.inheritance">
                                    <n-radio-button class="radio-btn radio-btn-left" label='AD' value='AD' :style="criteriaFormValue.criteriaCard.inheritance === 'AD' ? 'background-color: #2A82E4; color: #ffffff;' : ''"></n-radio-button>
                                    <n-radio-button class="radio-btn" label='AR' value='AR' :style="criteriaFormValue.criteriaCard.inheritance === 'AR' ? 'background-color: #2A82E4; color: #ffffff;' : ''"></n-radio-button>
                                    <n-radio-button class="radio-btn" label='X-Link' value='X-Link' :style="criteriaFormValue.criteriaCard.inheritance === 'X-Link' ? 'background-color: #2A82E4; color: #ffffff;' : ''"></n-radio-button>
                                    <n-radio-button class="radio-btn radio-btn-right" label='Y-Link' value='Y-Link' :style="criteriaFormValue.criteriaCard.inheritance === 'Y-Link' ? 'background-color: #2A82E4; color: #ffffff;' : ''"></n-radio-button>
                                </n-radio-group>
                            </n-form-item>
                            <!-- <n-form-item label="Related Criteria" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 10px">
                                <n-input-group style="width: 94%;">
                                    <span style="font-weight: bolder; font-size: 14px; color: #000; margin-right: 10px;">PM2 is</span>
                                    <n-switch :rail-style="SwitchStyle" size="small">
                                        <template #unchecked>
                                            <span>Unmet</span>
                                        </template>
                                        <template #checked>
                                            <span>Met</span>
                                        </template>
                                    </n-switch>
                                </n-input-group>
                            </n-form-item> -->
                            <n-form-item label="Proband" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 20px" path="criteriaCard.proband">
                                <n-input-group style="width: 94%;">
                                    <n-input-number style="--n-font-size: 12px; font-size: 12px;" size="small" min="0"  v-model:value="criteriaFormValue.criteriaCard.proband" clearable />
                                </n-input-group>
                            </n-form-item>
                        </div>
                        <div v-if="props.info.name==='BS3' || props.info.name==='PS3'">
                            <n-form-item label="Experiment" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 20px" path="ps3Criteria.experiment">
                                <n-input-group style="width: 94%;">
                                    <n-input placeholder="cellular mislocalisation of pendrin protein" style="font-size: 12px;" v-model:value="criteriaFormValue.ps3Criteria.experiment" />
                                </n-input-group>
                            </n-form-item>
                            <n-form-item label="Function change words" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 20px" path="ps3Criteria.funChange">
                                <n-input-group style="width: 94%;">
                                    <n-input placeholder="input function change words" style="font-size: 12px;" v-model:value="criteriaFormValue.ps3Criteria.funChange" />
                                </n-input-group>
                            </n-form-item>
                            <n-form-item label="Figure" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 10px">
                                <n-input-group style="width: 94%;">
                                    <div class="figure-img">

                                    </div>
                                </n-input-group>
                            </n-form-item>
                        </div>
                        <div v-if="props.info.name==='BS4' || props.info.name==='PP1' || props.info.name==='PP4' || props.info.name==='BP5'">
                            <n-form-item v-if="props.info.name !== 'BP5'" label="Phenotype" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 20px" path="criteriaCard.phenotype">
                                <n-input-group style="width: 94%;">
                                    <n-select size="small" v-model:value="criteriaFormValue.criteriaCard.phenotype" :options="phenotypeOptions" />
                                </n-input-group>
                            </n-form-item>
                            <n-form-item v-if="props.info.name === 'BP5'" label="Alternate explanation" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 20px" path="criteriaCard.phenotype">
                                <n-input-group style="width: 94%;">
                                    <n-input style="font-size: 12px;" placeholder="Input alternate explanation" />
                                </n-input-group>
                            </n-form-item>
                            <n-form-item v-if="props.info.name !== 'BP5'" label="Inheritance" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 20px" path="criteriaCard.inheritance">
                                <n-radio-group style="width: 94%;" v-model:value="criteriaFormValue.criteriaCard.inheritance">
                                    <n-radio-button class="radio-btn radio-btn-left" label='AD' value='AD' :style="criteriaFormValue.criteriaCard.inheritance === 'AD' ? 'background-color: #2A82E4; color: #ffffff;' : ''"></n-radio-button>
                                    <n-radio-button class="radio-btn" label='AR' value='AR' :style="criteriaFormValue.criteriaCard.inheritance === 'AR' ? 'background-color: #2A82E4; color: #ffffff;' : ''"></n-radio-button>
                                    <n-radio-button class="radio-btn" label='X-Link' value='X-Link' :style="criteriaFormValue.criteriaCard.inheritance === 'X-Link' ? 'background-color: #2A82E4; color: #ffffff;' : ''"></n-radio-button>
                                    <n-radio-button class="radio-btn radio-btn-right" label='Y-Link' value='Y-Link' :style="criteriaFormValue.criteriaCard.inheritance === 'Y-Link' ? 'background-color: #2A82E4; color: #ffffff;' : ''"></n-radio-button>
                                </n-radio-group>
                            </n-form-item>
                            <template v-if="props.info.name ==='BS4' || props.info.name ==='PP4' || props.info.name==='BP5'">
                                <n-form-item v-if="props.info.name==='BS4'" label="Genotype" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 20px" path="bs4Criteria.genotype">
                                    <n-radio-group style="width: 94%;" v-model:value="criteriaFormValue.bs4Criteria.genotype">
                                        <n-radio-button class="radio-btn radio-btn-left" label='Positive' value='Positive' :style="criteriaFormValue.bs4Criteria.genotype === 'Positive' ? 'background-color: #2A82E4; color: #ffffff;' : ''"></n-radio-button>
                                        <n-radio-button class="radio-btn radio-btn-right" label='Negative' value='Negative' :style="criteriaFormValue.bs4Criteria.genotype === 'Negative' ? 'background-color: #2A82E4; color: #ffffff;' : ''"></n-radio-button>
                                    </n-radio-group>
                                </n-form-item>
                                <n-form-item :label="props.info.name ==='BS4' ? 'Case number' : 'Proband number'" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 20px" path="criteriaCard.proband">
                                    <n-input-group style="width: 94%;">
                                        <n-input-number style="--n-font-size: 12px; font-size: 12px;" size="small" min="0" placeholder="0" v-model:value="criteriaFormValue.criteriaCard.proband" clearable />
                                    </n-input-group>
                                </n-form-item>
                            </template>
                            <template v-if="props.info.name==='PP1'">
                                <n-form-item label="test" label-style="font-size: 12px;font-weight: bolder;color: rgba(0,0,0,0)" style="--n-feedback-height: 10px" path="">
                                    <n-input-group style="width: 94%;">
                                        <div class="segregation-chart">
                                            <div class="sc-content">
                                                <div class="sc-left">
                                                    <span>Pedigree Chart</span>
                                                    <div class="pedigree-chart">
                                                        <img :src="hostUrl + '/download/' + props.info.pp1Figure" alt="">
                                                    </div>
                                                </div>
                                                <div class="sc-right">
                                                    <p>There is <span class="sc-span" style="color: #D43030">{{ criteriaFormValue.pp1Criteria.affectedSegregation ?? 0 }}</span> Affect and <span class="sc-span" style="color: #2A82E4">{{ criteriaFormValue.pp1Criteria.unaffectedSegregation ?? 0 }}</span> Unaffect segregatons.</p>
                                                    <p>LOD score is <span class="sc-span" style="color: #2A82E4">{{ criteriaFormValue.pp1Criteria.lod ?? 0 }}</span></p>
                                                    <p><n-button size="tiny" type="info">Upload</n-button> a new chart</p>
                                                </div>
                                            </div>
                                            <div class="aul-ses">
                                                <div class="ses-input">
                                                    <div>Affected Segregation</div>
                                                    <n-input-number style="--n-font-size: 12px; font-size: 12px; width: 130px;" size="small" min="0" placeholder="0" v-model:value="segregation.affectedSegregation" @update:value="caculatorLodScore" clearable />
                                                </div>
                                                <div class="ses-input" v-if="criteriaFormValue.criteriaCard.inheritance === 'AR'">
                                                    <div>Unaffected Segregation</div>
                                                    <n-input-number style="--n-font-size: 12px; font-size: 12px; width: 130px;" size="small" min="0" placeholder="0" v-model:value="segregation.unaffectedSegregation" @update:value="caculatorLodScore" clearable />
                                                </div>
                                                <div class="ses-input">
                                                    <div>LOD score</div>
                                                    <n-input style="--n-font-size: 12px; font-size: 12px; width: 130px;" size="small" placeholder="" v-model:value="segregation.lod" disabled />
                                                </div>
                                            </div>
                                        </div>
                                    </n-input-group>
                                </n-form-item>
                            </template>
                        </div>
                        <div v-if="props.info.name==='PS2' || props.info.name==='PM6'">
                            <n-form-item label="Status" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 20px" path="ps2Criteria.deNovo">
                                <n-radio-group style="width: 94%;" v-model:value="criteriaFormValue.ps2Criteria.deNovo">
                                    <n-radio-button class="radio-btn radio-btn-left" style="width: 150px" label='Confirmed de novo' value='Confirmed de novo' :style="criteriaFormValue.ps2Criteria.deNovo === 'Confirmed de novo' ? 'background-color: #2A82E4; color: #ffffff;' : ''"></n-radio-button>
                                    <n-radio-button class="radio-btn radio-btn-right" style="width: 150px" label='Assumed de novo' value='Assumed de novo' :style="criteriaFormValue.ps2Criteria.deNovo === 'Assumed de novo' ? 'background-color: #2A82E4; color: #ffffff;' : ''"></n-radio-button>
                                </n-radio-group>
                            </n-form-item>
                            <n-form-item label="Phenotype" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 20px" path="criteriaCard.phenotype">
                                <n-input-group style="width: 94%;">
                                    <n-select size="small" v-model:value="criteriaFormValue.criteriaCard.phenotype" :options="phenotypeOptions" />
                                </n-input-group>
                            </n-form-item>
                            <n-form-item label="Consistency" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 20px" path="ps2Criteria.phenotypicConsistency">
                                <n-input-group style="width: 94%;">
                                    <n-select size="small" v-model:value="criteriaFormValue.ps2Criteria.phenotypicConsistency" :options="consistencyOptions" />
                                </n-input-group>
                            </n-form-item>
                            <n-form-item label="Proband number" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 20px" path="criteriaCard.proband">
                                <n-input-group style="width: 94%;">
                                    <n-input-number style="--n-font-size: 12px; font-size: 12px;" size="small" min="0" placeholder="0" v-model:value="criteriaFormValue.criteriaCard.proband" clearable />
                                </n-input-group>
                            </n-form-item>
                        </div>
                        <n-form-item label="Evidence sentences" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 10px">
                            <n-input placeholder="Evidence sentences" v-model:value="criteriaFormValue.criteriaCard.sentence" type="textarea" style="font-size: 12px; width: 94%;" :autosize="{ minRows: 3, maxRows: 5 }"/>
                        </n-form-item>
                        <n-form-item label="Comments" label-style="font-size: 12px;font-weight: bolder;" style="--n-feedback-height: 10px">
                            <n-input placeholder="Comments" v-model:value="criteriaFormValue.criteriaCard.comment" type="textarea" style="font-size: 12px; width: 94%;" :autosize="{ minRows: 3, maxRows: 5 }"/>
                        </n-form-item>
                    </n-form>
                    <div class="form-btn">
                        <div class="hidden-ele"></div>
                        <div class="show-btn">
                            <n-button class="btn-sty" type="error" size="small" style="font-size: 12px;" @click="deleteCriteriaCard">Delete</n-button>
                            <n-button class="btn-sty" type="success" size="small" style="font-size: 12px;background-color: #7795BB;" @click="saveCurrentForm()">Save</n-button>
                        </div>
                    </div>
                </div>
            </n-scrollbar>
        </n-spin>
    </div>
</template>

<script setup lang='ts'>
    import { ref, reactive, computed, onMounted } from "vue";
    import { FormInst } from 'naive-ui';
    import { BounceLoader } from "vue3-spinner";
    import { phenotypeOptions, consistencyOptions, ancestryOptions } from "@/static/criteria";
    import { Message, Dialog } from "@/utils/utils";
    // import { SwitchStyle } from "@/utils/style";
    import { useSummary } from "@/store";
    import { updateEvidence, deleteEvidence } from "@/api/request";

    const props = defineProps(['info', 'router']);
    const summary = useSummary();
    const formRef = ref<FormInst | null>(null);
    const hostUrl = ref<string>(import.meta.env.HOST_URL);
    const loading = ref<boolean>(false);
    const emit = defineEmits(['delete', 'add']);
    let d:any = null;
    
    let criteriaFormValue = reactive({
        criteriaCard: {
            id: "",
            pmid: "",
            name: "",
            variantId: "",
            strength: "",
            sentence: "",
            comment: "",
            proband: 0,
            inheritance:"",
            phenotype:""
        },
        ps3Criteria: {
            experiment: "",
		    funChange: ""
        },
        pm3Criteria: {
            phase: "",
            allele2: "",
            clinvarPathogenicity: "",
            pathogenicity: ""
        },
        pp1Criteria: {
            affectedSegregation: 0,
            unaffectedSegregation: 0,
            lod: '',
        },
        bs4Criteria: {
            genotype: ""
        },
        ps2Criteria: {
            deNovo: "",
            phenotypicConsistency: ""
        },
        ps4Criteria: {
            alleleInPatient: null,
            totalAlleleInPatient: null,
            alleleInControl: null,
            totalAlleleInControl: null,
            ancestry: "",
            patientFreq: "",
            controlFreq: "",
            oddRatio: "",
            confidenceInterval: ""
        }
    })
    let segregation = reactive({
        affectedSegregation: 0,
        unaffectedSegregation: 0,
        lod: '',
    })
    const patientFreq = computed(()=> {
        if(criteriaFormValue.ps4Criteria.patientFreq === '' || criteriaFormValue.ps4Criteria.patientFreq === null) {
            return ''
        }else if(Number(criteriaFormValue.ps4Criteria.patientFreq) >= 0) {
            return String((Number(criteriaFormValue.ps4Criteria.patientFreq) * 100).toFixed(4))
        }
    })
    const controlFreq = computed(()=> {
        if(criteriaFormValue.ps4Criteria.controlFreq === '' || criteriaFormValue.ps4Criteria.controlFreq === null) {
            return ''
        }else if(Number(criteriaFormValue.ps4Criteria.controlFreq) >= 0) {
            return String((Number(criteriaFormValue.ps4Criteria.controlFreq) * 100).toFixed(4))
        }
    })
    const oddRatioFreq = computed(()=> {
        if(criteriaFormValue.ps4Criteria.oddRatio === '' || criteriaFormValue.ps4Criteria.oddRatio === null) {
            return ''
        }else{
            return String(Number(criteriaFormValue.ps4Criteria.oddRatio).toFixed(4))
        }
    })
    const strengthColor = ref({
        supporting: "",
        moderate: "",
        strong: "",
        verystrong: "",
        unmet: "",
        supportingColor:"",
        moderateColor:"",
        strongColor:"",
        verystrongColor:"",
        unmetColor:""
    })
    const matchColor = (criteria: string) => {
        if (criteria.split("_").length > 1) {
            switch (criteria.split("_")[1]) {
                case 'P':
                    strengthColor.value.supporting = summary.criteriaPalette[criteria.split("_")[0].slice(0, 1).toLocaleLowerCase() + "p"];
                    strengthColor.value.supportingColor = "white";
                    break;
                case 'M':
                    strengthColor.value.moderate = summary.criteriaPalette[criteria.split("_")[0].slice(0, 1).toLocaleLowerCase() + "m"];
                    strengthColor.value.moderateColor = "white";
                    break;
                case 'S':
                    strengthColor.value.strong = summary.criteriaPalette[criteria.split("_")[0].slice(0, 1).toLocaleLowerCase() + "s"];
                    strengthColor.value.strongColor = "white";
                    break;
                case 'Supporting':
                    strengthColor.value.supporting = summary.criteriaPalette[criteria.split("_")[0].slice(0, 1).toLocaleLowerCase() + "p"];
                    strengthColor.value.supportingColor = "white";
                    break;
                case 'Moderate':
                    strengthColor.value.moderate = summary.criteriaPalette[criteria.split("_")[0].slice(0, 1).toLocaleLowerCase() + "m"];
                    strengthColor.value.moderateColor = "white";
                    break;
                case 'Strong':
                    strengthColor.value.strong = summary.criteriaPalette[criteria.split("_")[0].slice(0, 1).toLocaleLowerCase() + "s"];
                    strengthColor.value.strongColor = "white";
                    break;
            }
        } else {
            switch (criteria.slice(0, 2).toLocaleLowerCase()) {
                case "pv":
                    strengthColor.value.verystrong = summary.criteriaPalette['pv'];
                    strengthColor.value.verystrongColor = "white";
                    break;
                case "ps":
                    strengthColor.value.strong = summary.criteriaPalette['ps'];
                    strengthColor.value.strongColor = "white";
                    break;
                case "bs":
                    strengthColor.value.strong = summary.criteriaPalette['bs'];
                    strengthColor.value.strong = "white";
                    break;
                case "pm":
                    strengthColor.value.moderate = summary.criteriaPalette['pm'];
                    strengthColor.value.moderateColor = "white";
                    break;
                case "pp":
                    strengthColor.value.supporting = summary.criteriaPalette['pp'];
                    strengthColor.value.supporting = "white";
                    break;
                case "ba":
                    strengthColor.value.verystrong = summary.criteriaPalette['ba'];
                    strengthColor.value.verystrong = "white";
                    break;
                case "bm":
                    strengthColor.value.moderate = summary.criteriaPalette['bm'];
                    strengthColor.value.moderateColor = "white";
                    break;
                case "bp":
                    strengthColor.value.moderate = summary.criteriaPalette['bp'];
                    strengthColor.value.moderateColor = "white";
                    break;
                case "un":
                    strengthColor.value.moderate = summary.criteriaPalette['unmet'];
                    strengthColor.value.unmetColor = "white";
                    break;
            }
        }
    };
    const showData = () => {
        const { info } = props;
        for(let i in criteriaFormValue.criteriaCard) {
            criteriaFormValue.criteriaCard[i] = i === 'proband' ? Number(info[i]) : info[i];
        }
        matchColor(info.name + "_" + info.strength);
        switch (info.name) {
            case "PM3":
            case "BP2":
                for(let i in criteriaFormValue.pm3Criteria) {
                    criteriaFormValue.pm3Criteria[i] = info[i];
                }
                break;
            case "PP1":
                criteriaFormValue.pp1Criteria.affectedSegregation = info.affectedSegregation;
                criteriaFormValue.pp1Criteria.unaffectedSegregation = info.unaffectedSegregation;
                criteriaFormValue.pp1Criteria.lod = info.lod;
                segregation.affectedSegregation = Number(info.affectedSegregation);
                segregation.unaffectedSegregation = Number(info.unaffectedSegregation);
                segregation.lod = info.lod;
                break;
            case "BS4":
                criteriaFormValue.bs4Criteria.genotype = info.genotype;
                break;
            case "PS2":
            case "PM6":
                criteriaFormValue.bs4Criteria.genotype = info.genotype;
                criteriaFormValue.ps2Criteria.deNovo = info.deNovo;
                criteriaFormValue.ps2Criteria.phenotypicConsistency = info.phenotypicConsistency;
                break;
            case "PS3":
            case "BS3":
                criteriaFormValue.ps3Criteria.experiment = info.experiment;
                criteriaFormValue.ps3Criteria.funChange = info.funChange;
                break;
            case "PS4":
                for(let i in criteriaFormValue.ps4Criteria) {
                    criteriaFormValue.ps4Criteria[i] = info[i];
                }
                break;
		}
    };
    const toArticleLink = () => {
        Dialog.destroyAll();
        props.router.push({ path: '/paper', query: { pmid: criteriaFormValue.criteriaCard.pmid } })
        // const routerUrl = props.router.resolve({
        //     path: '/paper',
        //     query: { pmid: criteriaFormValue.criteriaCard.pmid }
        // })
        // window.open(routerUrl.href, '_blank');
    }

    let rules = {
        criteriaCard: {
            pmid: {
                required: true,
                message: 'Please input pmid/pmcid/doi',
                trigger: 'blur'
            },
            strength: {
                required: true,
                message: 'Please select strength',
                trigger: 'change'
            },
            proband: {
                type: 'number',
                required: true,
                message: 'Please input Proband number',
                trigger: 'blur'
            },
            inheritance: {
                required: true,
                message: 'Please select Inheritance',
                trigger: 'change'
            },
            phenotype: {
                required: true,
                message: 'Please select phenotype',
                trigger: 'change'
            }
        },
        pm3Criteria: {
            phase: {
                required: true,
                message: 'Please select phase',
                trigger: 'change'
            },
            allele2: {
                required: true,
                message: 'Please input variant',
                trigger: 'blur',
            },
            pathogenicity: {
                required: true,
                message: 'Please select variant2 pathogenicity',
                trigger: 'change'
            }
        },
        ps3Criteria: {
            experiment: {
                required: true,
                message: 'Please input experiment',
                trigger: 'blur'
            },
            funChange: {
                required: true,
                message: 'Please input function change words',
                trigger: 'blur'
            }
        },
        ps4Criteria: {
            ancestry: {
                required: true,
                message: 'Please select ancestry',
                trigger: 'change'
            },
            patientFreq: {
                // type: 'number',
                required: true,
                message: '',
                trigger: 'blur'
            },
            controlFreq: {
                // type: 'number',
                required: true,
                message: '',
                trigger: 'blur'
            },
            oddRatio: {
                required: true,
                message: 'Please input Patient(s) and Control(s)'
            }
        },
        bs4Criteria: {
            genotype: {
                required: true,
                message: 'Please select genotype',
                trigger: 'change'
            }
        },
        ps2Criteria: {
            deNovo: {
                required: true,
                message: 'Please select status',
                trigger: 'change'
            },
            phenotypicConsistency: {
                required: true,
                message: 'Please select consistency',
                trigger: 'change'
            }
        },
        variant1: {
            required: false,
            message: ''
        },
        pathogenicity: {
            required: true,
        }
    }
    const calculatorFreq = (type: string) => {
        let { ps4Criteria } =  criteriaFormValue;
        const isNullP = ps4Criteria.alleleInPatient === '' || ps4Criteria.alleleInPatient === null;
        const isNullC = ps4Criteria.alleleInControl === '' || ps4Criteria.alleleInControl === null;
        const isNullTotalP = ps4Criteria.totalAlleleInPatient === '' || ps4Criteria.totalAlleleInPatient === null;
        const isNullTotalC = ps4Criteria.totalAlleleInControl === '' || ps4Criteria.totalAlleleInControl === null;
        const a = Number(ps4Criteria.alleleInPatient);
        const b = Number(ps4Criteria.totalAlleleInPatient)  - Number(ps4Criteria.alleleInPatient);
        const c = Number(ps4Criteria.alleleInControl);
        const d = Number(ps4Criteria.totalAlleleInControl) - Number(ps4Criteria.alleleInControl);
        if(type === 'patients') {
            if(isNullP || isNullTotalP) {
                ps4Criteria.patientFreq = '';
                ps4Criteria.oddRatio = '';
                ps4Criteria.confidenceInterval = '';
                return
            } 
            if(Number(ps4Criteria.totalAlleleInPatient) < a) {
                ps4Criteria.totalAlleleInPatient = null;
                ps4Criteria.patientFreq = '';
                ps4Criteria.oddRatio = '';
                ps4Criteria.confidenceInterval = '';
                Message.error('Total allele Cannot be less than Mutant allele');
            }else if(Number(ps4Criteria.totalAlleleInPatient) === 0) {
                ps4Criteria.totalAlleleInPatient = null;
                ps4Criteria.patientFreq = '';
                ps4Criteria.oddRatio = '';
                ps4Criteria.confidenceInterval = '';
                Message.error('Patients Total allele Cannot be 0');
            }else{
                ps4Criteria.patientFreq = String((a / Number(ps4Criteria.totalAlleleInPatient)));
            }
        }
        if(type === 'controls') {
            if(isNullC || isNullTotalC) {
                ps4Criteria.controlFreq = '';
                ps4Criteria.oddRatio = '';
                ps4Criteria.confidenceInterval = '';
                return
            }
            if(Number(ps4Criteria.totalAlleleInControl) < c) {
                ps4Criteria.totalAlleleInControl = null;
                ps4Criteria.controlFreq = '';
                ps4Criteria.oddRatio = '';
                ps4Criteria.confidenceInterval = '';
                Message.error('Total allele Cannot be less than Mutant allele');
            }else if(Number(ps4Criteria.totalAlleleInControl) === 0) {
                ps4Criteria.totalAlleleInControl = null;
                ps4Criteria.controlFreq = '';
                ps4Criteria.oddRatio = '';
                ps4Criteria.confidenceInterval = '';
                Message.error('Controls Total allele Cannot be 0');
            }else{
                ps4Criteria.controlFreq = String((c / Number(ps4Criteria.totalAlleleInControl)));
            }
        }
        if(isNullP || isNullC || isNullTotalP || isNullTotalC || Number(ps4Criteria.totalAlleleInPatient) === 0 || Number(ps4Criteria.totalAlleleInControl) === 0) {
            ps4Criteria.oddRatio = '';
            ps4Criteria.confidenceInterval = '';
            return
        }
        if(c === 0) {
            ps4Criteria.oddRatio = 'NA';
            ps4Criteria.confidenceInterval = 'NA';
            return
        }
        ps4Criteria.oddRatio = String(((a / c) / (b / d)));
        const r = Number(ps4Criteria.oddRatio);
        const cinit = Math.sqrt((1 / a) + (1 / b) + (1 / c) + (1 / d));
        let y = Math.log(r) - (1.96 * cinit);
        let z = Math.log(r) + (1.96 * cinit);
        y = Math.exp(y);
		z = Math.exp(z);
		y = Math.round(y * 10000) / 10000;
		z = Math.round(z * 10000) / 10000;
        ps4Criteria.confidenceInterval = 'from ' + y + ' to ' + z;
    }
    const caculatorLodScore = ()=> {    
        let affectedVal = segregation.affectedSegregation;
        let unAffectedVal = segregation.unaffectedSegregation;
        if(d !== null) {
            d.destroy();
        }
        if(criteriaFormValue.criteriaCard.inheritance === 'AR') {
            if(affectedVal === null && unAffectedVal === null) {
                if(!d) d = Message.warning('Please enter the correct value');
                return
            }
            if(affectedVal === null || unAffectedVal === null) {
                segregation.lod = '';
            }
            if(affectedVal !== null && unAffectedVal !== null) {
                segregation.lod = Math.log10(1 / ((Math.pow(0.25, affectedVal)*(Math.pow(0.75, unAffectedVal))))).toFixed(4);
            }
        }else{
            if(affectedVal === null) {
                segregation.lod = '';
                if(!d) d = Message.warning('Please enter the correct value');
                return 
            }else{
                segregation.lod = Math.log10(1 / Math.pow(0.5, affectedVal)).toFixed(4);
            }
        }
        d = null;
    }
    const deleteCriteriaCard = () => {
        let params = {
            id: criteriaFormValue.criteriaCard.id,
            name: criteriaFormValue.criteriaCard.name,
            variantId: criteriaFormValue.criteriaCard.variantId
        }
        Dialog.warning({
            title: 'Are you sure?',
            content: 'Are you sure delete?',
            positiveText: 'Sure',
            negativeText: 'Cancel',
            onPositiveClick: () => {
                loading.value = true;
                deleteEvidence(JSON.parse(JSON.stringify(params))).then(res=> {
                    if(res.data.code === 1000) {
                        emit('delete', criteriaFormValue.criteriaCard.id);
                        loading.value = false;
                        Message.success('Delete success');
                    }
                })
            },
            onNegativeClick: () => {}
        })
    }
    const saveCurrentForm = () => {
        formRef.value?.validate((errors) => {
            if (!errors) {
                loading.value = true;
                const { info } = props;
                let params = Object.values(criteriaFormValue).flat().reduce((pre:any, cur:any)=> {
                    return Object.assign(pre, cur)
                }, {})
                if(params.name === 'PP1') {
                    for(let i in segregation) {
                        params[i] = segregation[i];
                    }
                }
                let jsonParams = JSON.parse(JSON.stringify(info));
                for(let i in params) {
                    jsonParams[i] = params[i];
                }
                console.log(jsonParams)
                jsonParams.userId = '22';
                updateEvidence(JSON.parse(JSON.stringify(jsonParams))).then(res=> {
                    console.log(res)
                    if(res.data.code === 1000) {
                        if(info.id) {
                            Message.success('Update success');
                        }else{
                            Message.success('Add success');
                        }
                        params.id = res.data.data;
                        criteriaFormValue.criteriaCard.id = res.data.data;
                        emit('add', params);
                    }
                    loading.value = false;
                })
            } else {
                Message.warning('Please fill in the required information')
            }
        })
    }
    onMounted(() => {
        showData();
    });
</script>

<style lang='scss'>
    .createria-form {
        width: calc(100% - 10px);
        height: calc(100% - 10px);
        border: 1px solid #F1F1F1;
        border-radius: 8px;
        padding: 4px;
        background-color: #ffffff;
        .form-content {
            padding: 10px 6px;
            border-radius: 8px;
            font-size: 12px;
            .figure-img {
                width: 200px;
                height: 80px;
                border: 1px solid #1F5294;
                border-radius: 8px;
            }
        }
        .ps4-fre {
            display: flex;
            .fre-child {
                margin-right: 10px;
                width: 100px;
                color: #383838;
            }
            .fre-child:last-child {
                width: 115px;
            }
        }
        .ps4-caculator {
            display: flex;
            .cac-child {
                width: 200px;
                margin-right: 10px;
                color: #383838;
            }
        }
        .radio-btn {
            font-size: 12px;
            width: 100px;
            text-align: center;
        }
        .radio-btn-left {
            border-top-left-radius: 20px !important;
            border-bottom-left-radius: 20px !important;
            .n-radio-button__state-border {
                border-top-left-radius: 20px !important;
                border-bottom-left-radius: 20px !important;
            }
        }
        .radio-btn-right {
            border-top-right-radius: 20px !important;
            border-bottom-right-radius: 20px !important;
            .n-radio-button__state-border {
                border-top-right-radius: 20px !important;
                border-bottom-right-radius: 20px !important;
            }
        }
        .fre-child-input {
            width: 100px;
            background-color: #90BBEB;
            input {
                color: #000 !important;
            }
        }
        .variant-btn {
            font-size: 12px;
            background-color: #B6B6B6; 
            color: #ffffff;
        }
        .form-btn {
            display: flex;
            .hidden-ele {
                width: 160px;
            }
            .show-btn {
                width: calc(94% - 151px);
                display: flex;
                justify-content: space-between;
                .btn-sty {
                    width: 80px;
                }
            }
        }
        .segregation-chart {
            width: 100%;
            border: 1px solid rgba(42, 130, 228, 0.27);
            border-radius: 5px;
            padding: 5px 10px;
            color: #000000;
            .sc-content {
                display: flex;
                align-items: center;
                .sc-left {
                    width: 50%;
                    margin-right: 10px;
                    text-align: center;
                    .pedigree-chart {
                        width: 100%;
                        height: 160px;
                        background-color: #ffffff;
                        border-radius: 5px;
                    }
                }
                .sc-right {
                    width: 50%;
                    p {
                        line-height: 2.5;
                        .sc-span {
                            font-weight: bolder;
                            font-size: 16px;
                        }
                    }
                }
            }
            .aul-ses {
                margin: 10px 0;
                display: flex;
                align-items: center;
                .ses-input {
                    width: 30%;
                    margin-right: 10px;
                }
            }
        }
        .n-input .n-input__input-el {
            font-size: 12px !important;
        }
        .n-input .n-input__placeholder span {
            font-size: 12px !important;
        }
        .n-base-selection .n-base-selection-placeholder .n-base-selection-placeholder__inner {
            font-size: 12px !important;
        }
    }
</style>