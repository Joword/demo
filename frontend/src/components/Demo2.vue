<template>
  <div class="column">
    <!--    <p id="annotate" contenteditable="false">Hello, annotated world! To annotate, or not-->
    <!--      to annotate, that is the question.</p>-->
    <div id="annotate" style="position: absolute">
      <div id="viewer"></div>
    </div>
  </div>
  <div class="column" style="min-width: 20em;">
    <h2>The selector as JSON:</h2>
    <pre id="info"></pre>
  </div>
</template>

<script>
import {
  makeCreateRangeSelectorMatcher,
  createTextQuoteSelectorMatcher,
  createTextPositionSelectorMatcher,
  describeTextPosition,
  highlightText,
} from '@apache-annotator/dom';
import {makeRefinable} from '@apache-annotator/selector';
import "pdfjs-dist/web/pdf_viewer.css";
import * as pdfjsLib from "pdfjs-dist";
import * as pdfjsViewer from "pdfjs-dist/web/pdf_viewer";
import "pdfjs-dist/build/pdf.worker.entry";
import * as pdfjsSandbox from "pdfjs-dist/build/pdf.sandbox.js";

pdfjsLib.GlobalWorkerOptions.workerSrc = window.pdfjsWorker;

export default {
  name: "Demo2",
  components: {},
  async mounted() {
    if (!pdfjsLib.getDocument || !pdfjsViewer.PDFViewer) {
      // eslint-disable-next-line no-alert
      alert(
          "Please build the pdfjs-dist library using\n  `gulp dist-install`"
      );
    }
    const DEFAULT_URL = "/file/1479-7364-2-4-203.pdf";

    const SEARCH_FOR = ""; // try "Mozilla";

    const container = document.getElementById("annotate");

    const eventBus = new pdfjsViewer.EventBus();

    // (Optionally) enable hyperlinks within PDF files.
    const pdfLinkService = new pdfjsViewer.PDFLinkService({
      eventBus,
    });

    // (Optionally) enable find controller.
    const pdfFindController = new pdfjsViewer.PDFFindController({
      eventBus,
      linkService: pdfLinkService,
    });

    // (Optionally) enable scripting support.
    const pdfScriptingManager = new pdfjsViewer.PDFScriptingManager({
      eventBus,
      sandboxBundleSrc: pdfjsSandbox,
    });

    const pdfViewer = new pdfjsViewer.PDFViewer({
      container,
      eventBus,
      linkService: pdfLinkService,
      findController: pdfFindController,
      scriptingManager: pdfScriptingManager,
      enableScripting: true, // Only necessary in PDF.js version 2.10.377 and below.
    });
    pdfLinkService.setViewer(pdfViewer);
    pdfScriptingManager.setViewer(pdfViewer);

    eventBus.on("pagesinit", function () {
      // We can use pdfViewer now, e.g. let's change default scale.
      pdfViewer.currentScaleValue = "page-width";

      // We can try searching for things.
      if (SEARCH_FOR) {
        eventBus.dispatch("find", {type: "", query: SEARCH_FOR});
      }
    });

    // Loading document.
    const loadingTask = pdfjsLib.getDocument({
      url: DEFAULT_URL,
    });
    const pdfDocument = await loadingTask.promise;
    // Document loaded, specifying document for the viewer and
    // the (optional) linkService.
    pdfViewer.setDocument(pdfDocument);

    pdfLinkService.setDocument(pdfDocument, null);
  },
}

const EXAMPLE_SELECTORS = [];

let moduleState = {
  cleanupFunctions: [],
};

function cleanup() {
  let removeHighlight;
  while ((removeHighlight = moduleState.cleanupFunctions.shift())) {
    removeHighlight();
  }
  annotate.normalize();
  info.innerText = '';
}

const createMatcher = makeRefinable((selector) => {
  const innerCreateMatcher = {
    TextQuoteSelector: createTextQuoteSelectorMatcher,
    TextPositionSelector: createTextPositionSelectorMatcher,
    RangeSelector: makeCreateRangeSelectorMatcher(createMatcher),
  }[selector.type];

  if (!innerCreateMatcher) {
    throw new Error(`Unsupported selector type: ${selector.type}`);
  }

  return innerCreateMatcher(selector);
});

async function anchor(selector) {
  const matchAll = createMatcher(selector);
  const ranges = [];

  // First collect all matches, and only then highlight them; to avoid
  // modifying the DOM while the matcher is running.
  for await (const range of matchAll(annotate)) {
    ranges.push(range);
  }

  for (const range of ranges) {
    const removeHighlight = highlightText(range);
    moduleState.cleanupFunctions.push(removeHighlight);
  }

  info.innerText += JSON.stringify(selector, null, 2) + '\n\n';
}

async function onSelectionChange() {
  cleanup();
  // const describeMode = form.describeMode.value;
  const selection = document.getSelection();
  for (let i = 0; i < selection.rangeCount; i++) {
    const range = selection.getRangeAt(i);
    const selector = await describeTextPosition(range, annotate)
    await anchor(selector);
  }
}

function onSelectorExampleClick(event) {
  const exampleNumber = event.target.dataset.runExample;
  if (!exampleNumber) return;
  const selector = EXAMPLE_SELECTORS[exampleNumber];
  cleanup();
  anchor(selector);
  event.preventDefault();
}

function addEventListeners() {
  document.addEventListener('selectionchange', onSelectionChange);
  document.addEventListener('click', onSelectorExampleClick);
}

addEventListeners();

</script>

<style scoped>
a[data-run-example]:before {
  content: '📌 ';
}

#annotate {
  padding: 1em;
  border: 1px solid lightgrey;
  background: white;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

#info {
  height: 15em;
  overflow: scroll;
  background: white;
  color: #666666;
  resize: vertical;
}

</style>